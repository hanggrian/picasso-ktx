plugins {
    android("library")
    kotlin("android")
    dokka
    `maven-publish`
    signing
}

android {
    compileSdk = SDK_TARGET
    defaultConfig {
        minSdk = SDK_MIN
        targetSdk = SDK_TARGET
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        named("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
        named("androidTest") {
            setRoot("tests")
            manifest.srcFile("tests/AndroidManifest.xml")
            java.srcDir("tests/src")
            res.srcDir("tests/res")
            resources.srcDir("tests/src")
        }
    }
    libraryVariants.all {
        generateBuildConfigProvider.orNull?.enabled = false
    }
}

ktlint()

dependencies {
    implementation(kotlin("stdlib", VERSION_KOTLIN))
    implementation(square("picasso", VERSION_PICASSO))
    implementation(androidx("annotation", "annotation", "1.2.0"))
    androidTestImplementation(kotlin("test-junit", VERSION_KOTLIN))
    androidTestImplementation(androidx("appcompat", version = VERSION_ANDROIDX))
    androidTestImplementation(androidx("test", "core-ktx", VERSION_ANDROIDX_TEST))
    androidTestImplementation(androidx("test", "runner", VERSION_ANDROIDX_TEST))
    androidTestImplementation(androidx("test", "rules", VERSION_ANDROIDX_TEST))
    androidTestImplementation(androidx("test.ext", "junit-ktx", VERSION_ANDROIDX_JUNIT))
    androidTestImplementation(androidx("test.ext", "truth", VERSION_ANDROIDX_TRUTH))
    androidTestImplementation(androidx("test.espresso", "espresso-core", VERSION_ESPRESSO))
}

tasks {
    dokkaJavadoc {
        dokkaSourceSets {
            "main" {
                sourceLink {
                    localDirectory.set(projectDir.resolve("src"))
                    remoteUrl.set(getGithubRemoteUrl())
                    remoteLineSuffix.set("#L")
                }
            }
        }
    }
    dokkaHtml {
        outputDirectory.set(buildDir.resolve("dokka/$RELEASE_ARTIFACT"))
    }
}

mavenPublishAndroid(sources = android.sourceSets["main"].java.srcDirs)
