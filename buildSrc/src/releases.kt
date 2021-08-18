const val SDK_MIN = 14
const val SDK_TARGET = 30

const val RELEASE_GROUP = "com.hendraanggrian.appcompat"
const val RELEASE_ARTIFACT = "picasso-ktx"
const val RELEASE_VERSION = "0.1-SNAPSHOT"
const val RELEASE_DESCRIPTION = "Kotlin extensions for Picasso image loader"
const val RELEASE_GITHUB = "https://github.com/hendraanggrian/$RELEASE_ARTIFACT"

fun getGithubRemoteUrl(artifact: String = RELEASE_ARTIFACT) =
    `java.net`.URL("$RELEASE_GITHUB/tree/main/$artifact/src")