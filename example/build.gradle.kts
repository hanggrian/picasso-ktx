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
        applicationId = "com.example.pikasso"
        versionCode = 1
        versionName = "1.0"
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
    implementation(project(":pikasso-transformations"))
    implementation(project(":pikasso-placeholders"))

    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(anko("commons"))
    implementation(anko("design"))
    implementation(anko("sdk25-coroutines"))

    implementation(support("appcompat-v7", VERSION_SUPPORT))
    implementation(support("cardview-v7", VERSION_SUPPORT))
    implementation(support("design", VERSION_SUPPORT))
    implementation(support("percent", VERSION_SUPPORT))

    implementation(hendraanggrian("errorbar", VERSION_SUPPORT))
}