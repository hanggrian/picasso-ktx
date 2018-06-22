import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.kotlin.gradle.dsl.Coroutines.ENABLE

plugins {
    `android-application`
    kotlin("android")
    kotlin("android.extensions")
}

kotlin.experimental.coroutines = ENABLE

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
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT"))

    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(anko("commons"))
    implementation(anko("design"))
    implementation(anko("sdk25-coroutines"))

    implementation(support("appcompat-v7", VERSION_SUPPORT))
    implementation(support("cardview-v7", VERSION_SUPPORT))
    implementation(support("preference-v14", VERSION_SUPPORT))
    implementation(support("design", VERSION_SUPPORT))
    implementation(support("percent", VERSION_SUPPORT))

    implementation(slidingUpPanel())
    implementation(photoView())
    implementation(hendraanggrian("errorbar", VERSION_SUPPORT))
}