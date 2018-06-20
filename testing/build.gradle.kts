import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.tasks.JavaExec
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.creating
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.dokka.gradle.DokkaTask
import org.gradle.language.base.plugins.LifecycleBasePlugin.*

plugins {
    `android-library`
    kotlin("android")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionName = RELEASE_VERSION
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

val ktlint by configurations.creating

dependencies {
    api(junit())
    api(kotlin("test", VERSION_KOTLIN))
    api(anko("commons"))
    api(support("design", VERSION_SUPPORT))
    api(support("espresso-core", VERSION_ESPRESSO, "test", "espresso"))
    api(support("runner", VERSION_RUNNER, "test"))
    api(support("rules", VERSION_RULES, "test"))

    ktlint(ktlint())
}

tasks {
    "ktlint"(JavaExec::class) {
        get("check").dependsOn(this)
        group = VERIFICATION_GROUP
        inputs.dir("src")
        outputs.dir("src")
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.github.shyiko.ktlint.Main"
        args("--android", "src/**/*.kt")
    }
    "ktlintFormat"(JavaExec::class) {
        group = "formatting"
        inputs.dir("src")
        outputs.dir("src")
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.github.shyiko.ktlint.Main"
        args("--android", "-F", "src/**/*.kt")
    }
}