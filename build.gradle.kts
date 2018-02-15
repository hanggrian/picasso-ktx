buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(android())
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath(bintrayRelease())
        classpath(dokkaAndroid())
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
    tasks.withType(Javadoc::class.java).all {
        isEnabled = false
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

task<Wrapper>("wrapper") {
    gradleVersion = "4.4.1"
}

/** QUICK UPLOAD
./gradlew picasso-utils:bintrayUpload -PdryRun=false -PbintrayUser=hendraanggrian -PbintrayKey=
 */