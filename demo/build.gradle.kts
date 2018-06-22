import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE

plugins {
    `android-application`
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionName = RELEASE_VERSION
        applicationId = "$RELEASE_GROUP.$RELEASE_ARTIFACT.demo"
        versionCode = 1
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard.pro")
        }
    }
}

val ktlint by configurations.creating

dependencies {
    implementation(project(":$RELEASE_ARTIFACT"))

    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(anko("commons"))

    implementation(support("appcompat-v7", VERSION_SUPPORT))
    implementation(support("cardview-v7", VERSION_SUPPORT))
    implementation(support("preference-v14", VERSION_SUPPORT))
    implementation(support("design", VERSION_SUPPORT))
    implementation(support("percent", VERSION_SUPPORT))

    implementation(slidingUpPanel())
    implementation(photoView())
    implementation(hendraanggrian("errorbar", VERSION_SUPPORT))

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
}