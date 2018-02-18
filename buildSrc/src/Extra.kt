const val releaseUser = "hendraanggrian"
const val releaseGroup = "com.hendraanggrian"
const val releaseArtifact = "pikasso"
const val releaseVersion = "0.1"
const val releaseDesc = "Handy extension to Picasso with pre-loaded transformations and target placeholder"
const val releaseWeb = "https://github.com/hendraanggrian/pikasso"

const val minSdk = 14
const val targetSdk = 27
const val buildTools = "27.0.3"

const val kotlinVersion = "1.2.21"
const val supportVersion = "27.0.2"
const val picassoVersion = "2.5.2"
const val kotaVersion = "0.21"

const val runnerVersion = "1.0.1"
const val espressoVersion = "3.0.1"

fun Dependency.dokkaAndroid() = "org.jetbrains.dokka:dokka-android-gradle-plugin:0.9.15"
val Plugin.`dokka-android` get() = id("org.jetbrains.dokka-android")

fun Dependency.android() = "com.android.tools.build:gradle:3.0.1"
val Plugin.`android-library` get() = id("com.android.library")
val Plugin.`android-app` get() = id("com.android.application")

fun Dependency.bintrayRelease() = "com.novoda:bintray-release:0.8.0"
val Plugin.`bintray-release` get() = id("com.novoda.bintray-release")

fun Dependency.ktlint(): org.gradle.api.artifacts.Dependency = add("ktlint", "com.github.shyiko:ktlint:0.15.0")
fun Dependency.support(module: String, version: String, vararg suffixes: String) = "${StringBuilder("com.android.support").apply { suffixes.forEach { append(".$it") } }}:$module:$version"
fun Dependency.square(module: String, version: String) = "com.squareup.$module:$module:$version"
fun Dependency.hendraanggrian(module: String, version: String) = "com.hendraanggrian:$module:$version"
fun Dependency.junit() = "junit:junit:4.12"

private typealias Dependency = org.gradle.api.artifacts.dsl.DependencyHandler
private typealias Plugin = org.gradle.plugin.use.PluginDependenciesSpec