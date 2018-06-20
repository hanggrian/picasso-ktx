import org.gradle.kotlin.dsl.kotlin

plugins {
    `git-publish`
}

gitPublish {
    repoUri = RELEASE_WEBSITE
    branch = "gh-pages"
    contents.from(
        "pages",
        "../pikasso-commons/build/docs",
        "../pikasso-transformations/build/docs",
        "../pikasso-targets/build/docs")
}

tasks["gitPublishCopy"].dependsOn(
    ":pikasso-commons:dokka",
    ":pikasso-transformations:dokka",
    ":pikasso-targets:dokka")