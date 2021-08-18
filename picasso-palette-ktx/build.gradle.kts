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
    api(project(":$RELEASE_ARTIFACT"))
    api(androidx("palette", "palette-ktx", "1.0.0"))
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
        outputDirectory.set(buildDir.resolve("dokka/picasso-palette-ktx"))
    }
}

mavenPublishAndroid("picasso-pallete-ktx", sources = android.sourceSets["main"].java.srcDirs)