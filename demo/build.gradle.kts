plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionCode = 1
        versionName = RELEASE_VERSION
        applicationId = "$RELEASE_GROUP.demo"
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
    implementation(project(":$RELEASE_ARTIFACT"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))

    implementation(material())
    implementation(androidx("core"))
    implementation(androidx("appcompat"))
    implementation(androidx("preference"))
    implementation(hendraanggrian("material", "errorbar-ktx"))
}