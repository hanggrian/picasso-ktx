buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(android())
        classpath(kotlin("gradle-plugin", VERSION_KOTLIN))
        classpath(dokka())
        classpath(gitPublish())
        classpath(bintrayRelease())
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven("https://jitpack.io")
    }
    tasks.withType<Javadoc> {
        isEnabled = false
    }
}

tasks {
    register("clean", Delete::class) {
        delete(rootProject.buildDir)
    }
    register("wrapper", Wrapper::class) {
        gradleVersion = VERSION_GRADLE
    }
}