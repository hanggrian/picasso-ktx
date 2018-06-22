import com.android.build.gradle.api.AndroidSourceSet
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    `android-library`
    kotlin("android")
    dokka
    `bintray-release`
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionName = RELEASE_VERSION
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
        }
    }
    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

val ktlint by configurations.creating

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    api(square("picasso", VERSION_PICASSO)) {
        exclude("com.android.support")
    }

    implementation(support("support-annotations", VERSION_SUPPORT))
    implementation(support("support-compat", VERSION_SUPPORT))
    implementation(support("exifinterface", VERSION_SUPPORT))

    testImplementation(junit())
    androidTestImplementation(project(":testing"))

    ktlint(ktlint())
}

tasks {
    "ktlint"(JavaExec::class) {
        get("check").dependsOn(this)
        group = LifecycleBasePlugin.VERIFICATION_GROUP
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

    withType<DokkaTask> {
        outputDirectory = "$buildDir/docs"
        doFirst { file(outputDirectory).deleteRecursively() }
    }
}

publish {
    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = "$RELEASE_ARTIFACT-commons"
    publishVersion = RELEASE_VERSION
    desc = RELEASE_DESC
    website = RELEASE_WEBSITE
}
