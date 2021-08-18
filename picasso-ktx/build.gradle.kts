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
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
            java.srcDir("src")
            res.srcDir("res")
            resources.srcDir("src")
        }
        getByName("androidTest") {
            setRoot("tests")
            manifest.srcFile("tests/AndroidManifest.xml")
            java.srcDir("tests/src")
        }
    }
    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

ktlint()

dependencies {
    api(kotlin("stdlib", VERSION_KOTLIN))
    api(square("picasso", VERSION_PICASSO))
    implementation(androidx("annotation", "annotation", "1.2.0"))
    testImplementation(kotlin("test-junit", VERSION_KOTLIN))
    androidTestImplementation(project(":testing"))
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