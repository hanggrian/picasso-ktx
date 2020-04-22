plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(SDK_TARGET)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionCode = 1
        versionName = RELEASE_VERSION
        applicationId = "com.example.$RELEASE_ARTIFACT"
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
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

    implementation(material())
    implementation(androidx("core"))
    implementation(androidx("appcompat"))
    implementation(androidx("preference"))

    implementation(hendraanggrian("material", "bannerbar-ktx", "1.1.0"))
    implementation(hendraanggrian("prefy", "prefy-android", VERSION_PREFY))
    kapt(hendraanggrian("prefy", "prefy-compiler", VERSION_PREFY))
}
