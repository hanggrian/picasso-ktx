buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath(android())
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath(dokkaAndroid())
        classpath(bintrayRelease())
    }
}

allprojects {
    repositories {
        google()
        jcenter()
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