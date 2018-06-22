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
    api(project(":$RELEASE_ARTIFACT-commons"))
    api(project(":$RELEASE_ARTIFACT-transformations"))
    api(project(":$RELEASE_ARTIFACT-placeholders"))
    api(project(":$RELEASE_ARTIFACT-palette"))
}

gitPublish {
    repoUri = RELEASE_WEBSITE
    branch = "gh-pages"
    contents.from(
        "pages",
        "../$RELEASE_ARTIFACT-commons/build/docs",
        "../$RELEASE_ARTIFACT-transformations/build/docs",
        "../$RELEASE_ARTIFACT-placeholders/build/docs",
        "../$RELEASE_ARTIFACT-palette/build/docs")
}

tasks["gitPublishCopy"].dependsOn(
    ":$RELEASE_ARTIFACT-commons:dokka",
    ":$RELEASE_ARTIFACT-transformations:dokka",
    ":$RELEASE_ARTIFACT-placeholders:dokka",
    ":$RELEASE_ARTIFACT-palette:dokka")

publish {
    userOrg = RELEASE_USER
    groupId = RELEASE_GROUP
    artifactId = RELEASE_ARTIFACT
    publishVersion = RELEASE_VERSION
    desc = RELEASE_DESC
    website = RELEASE_WEBSITE
}