/*
 * Copyright 2018 The Android Open Source Project
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

package androidx.build

import androidx.build.ArtifactsPredicate.All
import androidx.build.ArtifactsPredicate.Benchmark
import androidx.build.ArtifactsPredicate.Exact
import androidx.build.ArtifactsPredicate.Group
import androidx.build.Strategy.Ignore
import androidx.build.Strategy.Prebuilts
import androidx.build.Strategy.TipOfTree

val RELEASE_RULE = docsRules("public", false) {
    prebuilts(LibraryGroups.ACTIVITY, "1.0.0-alpha06")
    prebuilts(LibraryGroups.ANNOTATION, "1.1.0-beta01")
    prebuilts(LibraryGroups.APPCOMPAT, "1.1.0-alpha04")
    prebuilts(LibraryGroups.ARCH_CORE, "2.1.0-alpha01")
    prebuilts(LibraryGroups.ASYNCLAYOUTINFLATER, "1.0.0")
    prebuilts(LibraryGroups.BIOMETRIC, "biometric", "1.0.0-alpha04")
    prebuilts(LibraryGroups.BROWSER, "1.0.0")
    ignore(LibraryGroups.CAR.group, "car-moderator")
    prebuilts(LibraryGroups.CAR, "car-cluster", "1.0.0-alpha5")
    prebuilts(LibraryGroups.CAR, "car", "1.0.0-alpha7")
            .addStubs("car/stubs/android.car.jar")
    prebuilts(LibraryGroups.CARDVIEW, "1.0.0")
    prebuilts(LibraryGroups.COLLECTION, "1.1.0-beta01")
    prebuilts(LibraryGroups.CONCURRENT, "1.0.0-alpha03")
    prebuilts(LibraryGroups.CONTENTPAGER, "1.0.0")
    prebuilts(LibraryGroups.COORDINATORLAYOUT, "1.1.0-alpha01")
    prebuilts(LibraryGroups.CORE, "core", "1.1.0-alpha05")
    prebuilts(LibraryGroups.CORE, "core-ktx", "1.1.0-alpha05")
    prebuilts(LibraryGroups.CURSORADAPTER, "1.0.0")
    prebuilts(LibraryGroups.CUSTOMVIEW, "1.0.0")
    prebuilts(LibraryGroups.DOCUMENTFILE, "1.0.0")
    prebuilts(LibraryGroups.DRAWERLAYOUT, "1.0.0")
    prebuilts(LibraryGroups.DYNAMICANIMATION, "dynamicanimation-ktx", "1.0.0-alpha02")
    prebuilts(LibraryGroups.DYNAMICANIMATION, "1.1.0-alpha01")
    prebuilts(LibraryGroups.EMOJI, "1.0.0")
    prebuilts(LibraryGroups.ENTERPRISE, "1.0.0-alpha01")
    prebuilts(LibraryGroups.EXIFINTERFACE, "1.1.0-alpha01")
    prebuilts(LibraryGroups.FRAGMENT, "1.1.0-alpha06")
    prebuilts(LibraryGroups.GRIDLAYOUT, "1.0.0")
    prebuilts(LibraryGroups.HEIFWRITER, "1.0.0")
    prebuilts(LibraryGroups.INTERPOLATOR, "1.0.0")
    prebuilts(LibraryGroups.LEANBACK, "1.1.0-alpha01")
    prebuilts(LibraryGroups.LEGACY, "1.0.0")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-savedstate-core")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-savedstate-fragment")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-viewmodel-fragment")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-livedata-ktx")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-livedata-core-ktx")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-compiler")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-runtime-eap")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-livedata-eap")
    ignore(LibraryGroups.LIFECYCLE.group, "lifecycle-runtime-eap-lint")
    prebuilts(LibraryGroups.LIFECYCLE, "lifecycle-viewmodel-savedstate", "1.0.0-alpha01")
    prebuilts(LibraryGroups.LIFECYCLE, "2.1.0-alpha04")
    prebuilts(LibraryGroups.LOADER, "1.1.0-beta01")
    prebuilts(LibraryGroups.LOCALBROADCASTMANAGER, "1.1.0-alpha01")
    prebuilts(LibraryGroups.MEDIA, "media", "1.1.0-alpha04")
    // TODO: Rename media-widget to media2-widget after 1.0.0-alpha06
    prebuilts(LibraryGroups.MEDIA, "media-widget", "1.0.0-alpha06")
    ignore(LibraryGroups.MEDIA2.group, "media2-widget")
    ignore(LibraryGroups.MEDIA2.group, "media2-exoplayer")
    prebuilts(LibraryGroups.MEDIA2, "1.0.0-alpha04")
    prebuilts(LibraryGroups.MEDIAROUTER, "1.1.0-alpha03")
    ignore(LibraryGroups.NAVIGATION.group, "navigation-testing")
    prebuilts(LibraryGroups.NAVIGATION, "2.1.0-alpha02")
    prebuilts(LibraryGroups.PAGING, "2.1.0")
    prebuilts(LibraryGroups.PALETTE, "1.0.0")
    prebuilts(LibraryGroups.PERCENTLAYOUT, "1.0.0")
    prebuilts(LibraryGroups.PERSISTENCE, "2.0.0")
    prebuilts(LibraryGroups.PREFERENCE, "preference-ktx", "1.1.0-alpha04")
    prebuilts(LibraryGroups.PREFERENCE, "1.1.0-alpha04")
    prebuilts(LibraryGroups.PRINT, "1.0.0")
    prebuilts(LibraryGroups.RECOMMENDATION, "1.0.0")
    prebuilts(LibraryGroups.RECYCLERVIEW, "recyclerview", "1.1.0-alpha04")
    prebuilts(LibraryGroups.RECYCLERVIEW, "recyclerview-selection", "1.1.0-alpha01")
    prebuilts(LibraryGroups.REMOTECALLBACK, "1.0.0-alpha01")
    ignore(LibraryGroups.ROOM.group, "room-common-java8")
    prebuilts(LibraryGroups.ROOM, "2.1.0-alpha06")
    prebuilts(LibraryGroups.SAVEDSTATE, "1.0.0-alpha02")
    prebuilts(LibraryGroups.SHARETARGET, "1.0.0-alpha01")
    prebuilts(LibraryGroups.SLICE, "slice-builders", "1.0.0")
    prebuilts(LibraryGroups.SLICE, "slice-builders-ktx", "1.0.0-alpha6")
    prebuilts(LibraryGroups.SLICE, "slice-core", "1.0.0")
    // TODO: land prebuilts
//    prebuilts(LibraryGroups.SLICE.group, "slice-test", "1.0.0")
    ignore(LibraryGroups.SLICE.group, "slice-test")
    prebuilts(LibraryGroups.SLICE, "slice-view", "1.0.0")
    prebuilts(LibraryGroups.SLIDINGPANELAYOUT, "1.0.0")
    prebuilts(LibraryGroups.SWIPEREFRESHLAYOUT, "1.1.0-alpha01")
    prebuilts(LibraryGroups.TEXTCLASSIFIER, "1.0.0-alpha02")
    prebuilts(LibraryGroups.TRANSITION, "1.1.0-beta01")
    prebuilts(LibraryGroups.TVPROVIDER, "1.0.0")
    prebuilts(LibraryGroups.VECTORDRAWABLE, "1.1.0-alpha01")
    prebuilts(LibraryGroups.VECTORDRAWABLE, "vectordrawable-animated", "1.1.0-alpha01")
    prebuilts(LibraryGroups.VERSIONEDPARCELABLE, "1.1.0-alpha02")
    prebuilts(LibraryGroups.VIEWPAGER, "1.0.0")
    prebuilts(LibraryGroups.VIEWPAGER2, "1.0.0-alpha03")
    prebuilts(LibraryGroups.WEAR, "1.0.0")
            .addStubs("wear/wear_stubs/com.google.android.wearable-stubs.jar")
    prebuilts(LibraryGroups.WEBKIT, "1.0.0")
    ignore(LibraryGroups.WORKMANAGER.group, "work-gcm")
    prebuilts(LibraryGroups.WORKMANAGER, "2.0.1-rc01")
    default(Ignore)
}

val TIP_OF_TREE = docsRules("tipOfTree", true) {
    // TODO: remove once we'll figure out our strategy about it
    ignore(LibraryGroups.CONCURRENT.group)
    default(TipOfTree)
}

/**
 * Rules are resolved in addition order. So if you have two rules that specify how docs should be
 * built for a module, first defined rule wins.
 */
fun docsRules(
    name: String,
    offline: Boolean,
    init: PublishDocsRulesBuilder.() -> Unit
): PublishDocsRules {
    val f = PublishDocsRulesBuilder(name, offline)
    f.init()
    return f.build()
}

class PublishDocsRulesBuilder(private val name: String, private val offline: Boolean) {

    private val rules: MutableList<DocsRule> = mutableListOf(DocsRule(Benchmark, Ignore))
    /**
     * docs for projects within [groupName] will be built from sources.
     */
    fun tipOfTree(groupName: String) {
        rules.add(DocsRule(Group(groupName), TipOfTree))
    }

    /**
     * docs for a project with the given [groupName] and [name] will be built from sources.
     */
    fun tipOfTree(groupName: String, name: String) {
        rules.add(DocsRule(Exact(groupName, name), TipOfTree))
    }

    /**
     * docs for a project with the given [groupName] and [name] will be built from a prebuilt with
     * the given [version].
     */
    fun prebuilts(libraryGroup: LibraryGroup, moduleName: String, version: String): Prebuilts {
        val strategy = Prebuilts(Version(version))
        rules.add(DocsRule(Exact(libraryGroup.group, moduleName), strategy))
        return strategy
    }

    /**
     * docs for projects within [groupName] will be built from prebuilts with the given [version]
     */
    fun prebuilts(libraryGroup: LibraryGroup, version: String) =
            prebuilts(libraryGroup, Version(version))

    /**
     * docs for projects within [groupName] will be built from prebuilts with the given [version]
     */
    fun prebuilts(libraryGroup: LibraryGroup, version: Version): Prebuilts {
        val strategy = Prebuilts(version)
        rules.add(DocsRule(Group(libraryGroup.group), strategy))
        return strategy
    }

    /**
     * defines a default strategy for building docs
     */
    fun default(strategy: Strategy) {
        rules.add(DocsRule(All, strategy))
    }

    /**
     * docs for projects within [groupName] won't be built
     */
    fun ignore(groupName: String) {
        rules.add(DocsRule(Group(groupName), Ignore))
    }

    /**
     * docs for a specified project won't be built
     */
    fun ignore(groupName: String, name: String) {
        rules.add(DocsRule(Exact(groupName, name), Ignore))
    }

    fun build() = PublishDocsRules(name, offline, rules)
}

sealed class ArtifactsPredicate {
    abstract fun apply(inGroup: String, inName: String): Boolean
    object All : ArtifactsPredicate() {
        override fun apply(inGroup: String, inName: String) = true
    }
    class Group(val group: String) : ArtifactsPredicate() {
        override fun apply(inGroup: String, inName: String) = inGroup == group
        override fun toString() = "\"$group\""
    }
    class Exact(val group: String, val name: String) : ArtifactsPredicate() {
        override fun apply(inGroup: String, inName: String) = group == inGroup && name == inName
        override fun toString() = "\"$group\", \"$name\""
    }

    object Benchmark : ArtifactsPredicate() {
        override fun apply(inGroup: String, inName: String) = inName.endsWith("-benchmark")
    }
}

data class DocsRule(val predicate: ArtifactsPredicate, val strategy: Strategy) {
    override fun toString(): String {
        if (predicate is All) {
            return "default($strategy)"
        }
        return when (strategy) {
            is Prebuilts -> "prebuilts($predicate, \"${strategy.version}\")"
            is Ignore -> "ignore($predicate)"
            is TipOfTree -> "tipOfTree($predicate)"
        }
    }
}

sealed class Strategy {
    object TipOfTree : Strategy()
    object Ignore : Strategy()
    class Prebuilts(val version: Version) : Strategy() {
        var stubs: MutableList<String>? = null
        fun addStubs(path: String) {
            if (stubs == null) {
                stubs = mutableListOf()
            }
            stubs!!.add(path)
        }

        override fun toString() = "Prebuilts(\"$version\")"
        fun dependency(extension: AndroidXExtension): String {
            return "${extension.mavenGroup?.group}:${extension.project.name}:$version"
        }
    }
}

class PublishDocsRules(val name: String, val offline: Boolean, private val rules: List<DocsRule>) {
    fun resolve(extension: AndroidXExtension): DocsRule? {
        val mavenGroup = extension.mavenGroup
        return if (mavenGroup == null) null else resolve(mavenGroup.group, extension.project.name)
    }

    fun resolve(groupName: String, moduleName: String): DocsRule {
        return rules.find { it.predicate.apply(groupName, moduleName) } ?: throw Error()
    }
}
