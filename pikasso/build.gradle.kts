import org.gradle.kotlin.dsl.kotlin

plugins {
    `android-library`
    `git-publish`
    `bintray-release`
}

android {
    compileSdkVersion(SDK_TARGET)
    buildToolsVersion(BUILD_TOOLS)
    defaultConfig {
        minSdkVersion(SDK_MIN)
        targetSdkVersion(SDK_TARGET)
        versionName = RELEASE_VERSION
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }
    sourceSets {
        getByName("main") {
            manifest.srcFile("AndroidManifest.xml")
        }
    }
    libraryVariants.all {
        generateBuildConfig?.enabled = false
    }
}

dependencies {
    api(project(":pikasso-commons"))
    api(project(":pikasso-transformations"))
    api(project(":pikasso-placeholders"))
    api(project(":pikasso-palette"))
}

gitPublish {
    repoUri = RELEASE_WEBSITE
    branch = "gh-pages"
    contents.from(
        "pages",
        "../pikasso-commons/build/docs",
        "../pikasso-transformations/build/docs",
        "../pikasso-placeholders/build/docs",
        "../pikasso-palette/build/docs")
}

tasks["gitPublishCopy"].dependsOn(
    ":pikasso-commons:dokka",
    ":pikasso-transformations:dokka",
    ":pikasso-placeholders:dokka",
    ":pikasso-palette:dokka")

publish {
    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = RELEASE_ARTIFACT
    publishVersion = RELEASE_VERSION
    desc = RELEASE_DESC
    website = RELEASE_WEBSITE
}