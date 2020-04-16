plugins {
    android("library")
    kotlin("android")
}

android {
    compileSdkVersion(SDK_TARGET)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionName = RELEASE_VERSION
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDirs("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
    }
    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

dependencies {
    implementation(kotlin("test-junit", VERSION_KOTLIN))
    implementation(material())
    implementation(androidx("test", "core-ktx", VERSION_ANDROIDX_TEST))
    implementation(androidx("test", "runner", VERSION_ANDROIDX_TEST))
    implementation(androidx("test", "rules", VERSION_ANDROIDX_TEST))
    implementation(androidx("test.ext", "junit-ktx", VERSION_ANDROIDX_JUNIT))
    implementation(androidx("test.ext", "truth", VERSION_ANDROIDX_TRUTH))
    implementation(androidx("test.espresso", "espresso-core", VERSION_ESPRESSO))
}
