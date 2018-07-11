const val RELEASE_USER = "hendraanggrian"
const val RELEASE_ARTIFACT = "pikasso"
const val RELEASE_GROUP = "com.$RELEASE_USER.$RELEASE_ARTIFACT"
const val RELEASE_VERSION = "0.2"
const val RELEASE_DESC = "Kotlin extensions for Picasso image loader"
const val RELEASE_WEBSITE = "https://github.com/$RELEASE_USER/$RELEASE_ARTIFACT"

val bintrayUserEnv = System.getenv("BINTRAY_USER")
val bintrayKeyEnv = System.getenv("BINTRAY_KEY")