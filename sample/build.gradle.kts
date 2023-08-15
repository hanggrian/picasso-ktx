plugins {
    android("application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdk = SDK_TARGET
    defaultConfig {
        minSdk = SDK_MIN
        targetSdk = SDK_TARGET
        versionCode = 1
        versionName = RELEASE_VERSION
        applicationId = "com.example.picasso"
        multiDexEnabled = true
    }
    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard.pro")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard.pro")
        }
    }
    lint.isAbortOnError = false
}

dependencies {
    implementation(project(":$RELEASE_ARTIFACT"))
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(material())
    implementation(androidx("multidex", version = VERSION_MULTIDEX))
    implementation(androidx("core"))
    implementation(androidx("appcompat"))
    implementation(androidx("preference", "preference-ktx", "1.1.1"))
    implementation(hendraanggrian("material", "bannerbar-ktx", "$VERSION_ANDROIDX-SNAPSHOT"))
    implementation(hendraanggrian("auto", "prefs-android", VERSION_PREFY))
    kapt(hendraanggrian("auto", "prefs-compiler", VERSION_PREFY))
}
