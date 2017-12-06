plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(targetSdk)
    buildToolsVersion(buildTools)
    defaultConfig {
        minSdkVersion(minSdk)
        targetSdkVersion(targetSdk)
        applicationId = "com.example.picassoutils"
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
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    lintOptions {
        isAbortOnError = false
    }
}

dependencies {
    compile(project(":picasso-utils"))

    compile(kotlin("stdlib", kotlinVersion))

    compile(support("appcompat-v7", supportVersion))
    compile(support("cardview-v7", supportVersion))
    compile(support("design", supportVersion))
    compile(support("percent", supportVersion))

    compile(hendraanggrian("kota", "0.21"))
}

fun DependencyHandler.hendraanggrian(module: String, version: String) = "com.hendraanggrian:$module:$version"