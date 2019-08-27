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

val ktlint by configurations.creating

dependencies {
    api(junit())
    api(kotlin("test", VERSION_KOTLIN))
    api(material())
    api(androidx("core", "core-ktx"))
    api(androidx("appcompat"))
    api(androidx("test.espresso", "espresso-core", VERSION_ESPRESSO))
    api(androidx("test", "runner", VERSION_RUNNER))
    api(androidx("test", "rules", VERSION_RULES))

    ktlint(ktlint())
}

tasks {
    register("ktlint", JavaExec::class) {
        group = LifecycleBasePlugin.VERIFICATION_GROUP
        inputs.dir("src")
        outputs.dir("src")
        description = "Check Kotlin code style."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "src/**/*.kt")
    }
    "check" {
        dependsOn("ktlint")
    }
    register("ktlintFormat", JavaExec::class) {
        group = "formatting"
        inputs.dir("src")
        outputs.dir("src")
        description = "Fix Kotlin code style deviations."
        classpath = ktlint
        main = "com.pinterest.ktlint.Main"
        args("--android", "-F", "src/**/*.kt")
    }
}
