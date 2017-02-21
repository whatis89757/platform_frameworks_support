/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.support.room.processor

import com.android.support.room.ext.getAsBoolean
import com.android.support.room.ext.getAsString
import com.android.support.room.ext.getAsStringList
import com.android.support.room.ext.hasAnnotation
import com.android.support.room.processor.ProcessorErrors.INDEX_COLUMNS_CANNOT_BE_EMPTY
import com.android.support.room.vo.Entity
import com.android.support.room.vo.Index
import com.android.support.room.vo.Pojo
import com.android.support.room.vo.Warning
import com.google.auto.common.AnnotationMirrors
import com.google.auto.common.MoreElements
import com.google.auto.common.MoreTypes
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.AnnotationValue
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.SimpleAnnotationValueVisitor6

class EntityProcessor(baseContext: Context, val element: TypeElement) {
    val context = baseContext.fork(element)

    fun process(): Entity {
        context.checker.hasAnnotation(element, com.android.support.room.Entity::class,
                ProcessorErrors.ENTITY_MUST_BE_ANNOTATED_WITH_ENTITY)
        val pojo = PojoProcessor(
                baseContext = context,
                element = element,
                bindingScope = FieldProcessor.BindingScope.TWO_WAY,
                parent = null).process()
        val annotation = MoreElements.getAnnotationMirror(element,
                com.android.support.room.Entity::class.java).orNull()
        val tableName: String
        val entityIndices: List<Index>
        val inheritSuperIndices: Boolean
        if (annotation != null) {
            val annotationValue = AnnotationMirrors
                    .getAnnotationValue(annotation, "tableName").value.toString()
            if (annotationValue == "") {
                tableName = element.simpleName.toString()
            } else {
                tableName = annotationValue
            }
            entityIndices = extractIndices(annotation, tableName)
            inheritSuperIndices = AnnotationMirrors
                    .getAnnotationValue(annotation, "inheritSuperIndices").getAsBoolean(false)
        } else {
            tableName = element.simpleName.toString()
            entityIndices = emptyList()
            inheritSuperIndices = false
        }
        context.checker.notBlank(tableName, element,
                ProcessorErrors.ENTITY_TABLE_NAME_CANNOT_BE_EMPTY)

        val fieldIndices = pojo.fields
                .filter { it.indexed }
                .map {
                    if (it.parent != null) {
                        it.indexed = false
                        context.logger.w(Warning.INDEX_FROM_DECOMPOSED_FIELD_IS_DROPPED, it.element,
                                ProcessorErrors.droppedDecomposedFieldIndex(
                                        it.getPath(), element.qualifiedName.toString()))
                        null
                    } else if (it.element.enclosingElement != element && !inheritSuperIndices) {
                        it.indexed = false
                        context.logger.w(Warning.INDEX_FROM_PARENT_FIELD_IS_DROPPED,
                                ProcessorErrors.droppedSuperClassFieldIndex(
                                        it.columnName, element.toString(),
                                        it.element.enclosingElement.toString()
                                ))
                        null
                    } else {
                        Index(
                                name = createIndexName(listOf(it.columnName), tableName),
                                unique = false,
                                columnNames = listOf(it.columnName)
                        )
                    }
                }.filterNotNull()
        val superIndices = loadSuperIndices(element.superclass, tableName, inheritSuperIndices)
        val indices = entityIndices + fieldIndices + superIndices
        validateIndices(indices, pojo)

        val entity = Entity(element = element,
                tableName = tableName,
                type = pojo.type,
                fields = pojo.fields,
                decomposedFields = pojo.decomposedFields,
                indices = indices)
        context.checker.check(entity.primaryKeys.isNotEmpty(), element,
                ProcessorErrors.MISSING_PRIMARY_KEY)
        return entity
    }

    private fun validateIndices(indices: List<Index>, pojo: Pojo) {
        // check for columns
        indices.forEach {
            context.checker.check(it.columnNames.isNotEmpty(), element,
                    INDEX_COLUMNS_CANNOT_BE_EMPTY)
            it.columnNames.forEach { indexColumn ->
                if (!pojo.fields.any { it.columnName == indexColumn }) {
                    context.logger.e(element, ProcessorErrors.indexColumnDoesNotExist(
                            indexColumn, pojo.fields.map { it.columnName }
                    ))
                }
            }
        }

        // check for duplicate indices
        indices
                .groupBy { it.name }
                .filter { it.value.size > 1 }
                .forEach {
                    context.logger.e(element, ProcessorErrors.duplicateIndexInEntity(it.key))
                }

        // see if any decomposed field is an entity with indices, if so, report a warning
        pojo.decomposedFields.forEach { decomposed ->
            val decomposedElement = decomposed.pojo.element
            val subEntityAnnotation = MoreElements.getAnnotationMirror(decomposedElement,
                    com.android.support.room.Entity::class.java).orNull()
            subEntityAnnotation?.let {
                val subIndices = extractIndices(subEntityAnnotation, "")
                if (subIndices.isNotEmpty()) {
                    context.logger.w(Warning.INDEX_FROM_DECOMPOSED_ENTITY_IS_DROPPED,
                            decomposed.field.element, ProcessorErrors.droppedDecomposedIndex(
                            entityName = decomposed.pojo.typeName.toString(),
                            fieldPath = decomposed.field.getPath(),
                            grandParent = element.qualifiedName.toString()))
                }
            }
        }
    }

    // check if parent is an Entity, if so, report its annotation indices
    private fun loadSuperIndices(typeMirror: TypeMirror?, tableName: String, inherit: Boolean)
            : List<Index> {
        if (typeMirror == null || typeMirror.kind == TypeKind.NONE) {
            return emptyList()
        }
        val parentElement = MoreTypes.asTypeElement(typeMirror)
        val myIndices = MoreElements.getAnnotationMirror(parentElement,
                com.android.support.room.Entity::class.java).orNull()?.let { annotation ->
            val indices = extractIndices(annotation, tableName = "super")
            if (inherit) {
                // rename them
                indices.map {
                    Index(
                            name = createIndexName(it.columnNames, tableName),
                            unique = it.unique,
                            columnNames = it.columnNames)
                }
            } else {
                context.logger.w(Warning.INDEX_FROM_PARENT_IS_DROPPED,
                        parentElement,
                        ProcessorErrors.droppedSuperClassIndex(
                                childEntity = element.qualifiedName.toString(),
                                superEntity = parentElement.qualifiedName.toString()))
                emptyList()
            }
        } ?: emptyList()
        return myIndices + loadSuperIndices(parentElement.superclass, tableName, inherit)
    }

    companion object {
        private fun extractIndices(annotation: AnnotationMirror, tableName: String)
                : List<Index> {
            val arrayOfIndexAnnotations = AnnotationMirrors.getAnnotationValue(annotation,
                    "indices")
            return INDEX_LIST_VISITOR.visit(arrayOfIndexAnnotations, tableName)
        }

        private val INDEX_LIST_VISITOR = object
            : SimpleAnnotationValueVisitor6<List<Index>, String>() {
            override fun visitArray(values: MutableList<out AnnotationValue>?, tableName: String)
                    : List<Index> {
                return values?.map {
                    INDEX_VISITOR.visit(it, tableName)
                }?.filterNotNull() ?: emptyList<Index>()
            }
        }

        private val INDEX_VISITOR = object : SimpleAnnotationValueVisitor6<Index?, String>() {
            override fun visitAnnotation(a: AnnotationMirror?, tableName: String): Index? {
                val columnNames = AnnotationMirrors.getAnnotationValue(a, "value").getAsStringList()
                val unique = AnnotationMirrors.getAnnotationValue(a, "unique").getAsBoolean(false)
                val nameValue = AnnotationMirrors.getAnnotationValue(a, "name")
                        .getAsString("")
                val name = if (nameValue == null || nameValue == "") {
                    createIndexName(columnNames, tableName)
                } else {
                    nameValue
                }
                return Index(name, unique, columnNames)
            }
        }

        private fun createIndexName(columnNames: List<String>, tableName: String): String {
            return "index_" + tableName + "_" + columnNames.joinToString("_")
        }
    }
}
