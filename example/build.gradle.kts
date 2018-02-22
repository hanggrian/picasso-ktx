import org.gradle.kotlin.dsl.kotlin

plugins {
    `android-app`
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(targetSdk)
    buildToolsVersion(buildTools)
    defaultConfig {
        minSdkVersion(minSdk)
        targetSdkVersion(targetSdk)
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
}

dependencies {
    implementation(project(":pikasso"))
    implementation(kotlin("stdlib", kotlinVersion))
    implementation(support("appcompat-v7", supportVersion))
    implementation(support("cardview-v7", supportVersion))
    implementation(support("design", supportVersion))
    implementation(support("percent", supportVersion))
    implementation(hendraanggrian("kota", kotaVersion))
}