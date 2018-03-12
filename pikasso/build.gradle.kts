import org.gradle.kotlin.dsl.kotlin
import org.gradle.language.base.plugins.LifecycleBasePlugin.*
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `android-library`
    kotlin("android")
    `dokka`
    `git-publish`
    `bintray-release`
}

android {
    compileSdkVersion(targetSdk)
    buildToolsVersion(buildTools)
    defaultConfig {
        minSdkVersion(minSdk)
        targetSdkVersion(targetSdk)
        versionName = releaseVersion
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
        getByName("androidTest") {
            setRoot("tests")
            manifest.srcFile("tests/AndroidManifest.xml")
            java.srcDir("tests/src")
            res.srcDir("tests/res")
            resources.srcDir("tests/src")
        }
    }
    libraryVariants.all {
        generateBuildConfig.enabled = false
    }
}

val ktlint by configurations.creating

dependencies {
    compile(kotlin("stdlib", kotlinVersion))
    compile(support("support-annotations", supportVersion))
    compile(square("picasso", picassoVersion))

    testImplementation(junit())
    androidTestImplementation(kotlin("test", kotlinVersion))
    androidTestImplementation(support("design", supportVersion))
    androidTestImplementation(support("runner", runnerVersion, "test"))
    androidTestImplementation(support("espresso-core", espressoVersion, "test", "espresso"))
    androidTestImplementation(androidKtx())

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

    val dokka by getting(DokkaTask::class) {
        outputDirectory = "$buildDir/docs"
        doFirst { file(outputDirectory).deleteRecursively() }
    }
    gitPublish {
        repoUri = releaseWeb
        branch = "gh-pages"
        contents.from(dokka.outputDirectory)
    }
    get("gitPublishCopy").dependsOn(dokka)
}

publish {
    userOrg = releaseUser
    groupId = releaseGroup
    artifactId = releaseArtifact
    publishVersion = releaseVersion
    desc = releaseDesc
    website = releaseWeb
}