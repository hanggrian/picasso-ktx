[![version](https://img.shields.io/maven-central/v/com.hendraanggrian.appcompat/picasso-ktx)](https://search.maven.org/artifact/com.hendraanggrian.appcompat/picasso-ktx)
[![build](https://img.shields.io/travis/com/hendraanggrian/picasso-ktx)](https://www.travis-ci.com/github/hendraanggrian/picasso-ktx)
[![ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081)](https://ktlint.github.io/)

Picasso KTX
===========

![](images/demo_transformation.gif)
![](images/demo_palette.gif)

Kotlin extensions for Picasso image loader.
* Invoke callback and target with Kotlin DSL.
* Use pre-loaded transformations with extension functions.
* Supports material components Palette alongside too.

Download
--------

```gradle
dependencies {
    compile "com.hendraanggrian.appcompat:picasso-ktx:$version"
}
```

Usage
-----

### Common

Call `picasso` to get global instance of `Picasso`.
Or `buildPicasso` to invoke `Picasso.Builder`.

```kotlin
picasso.load(url).into(imageView)

val myPicasso = buildPicasso {
    loggingEnabled(true)
    memoryCache(Cache.NONE)
    listener { picasso, uri, exception -> }
}
myPicasso.load(url).into(imageView)
```

Clean declaration of `Callback` when loading images into `ImageView`. 

```kotlin
picasso.load(url).into(imageView) {
    onSuccess {
        celebrate()
    }
}
```

Or into `Target` with Kotlin DSL.

```kotlin
picasso.load(url).into {
    onLoaded { bitmap, from ->
        process(bitmap)
    }
}
```

### Transformations

Transform request using Kotlin extension functions.

```kotlin
picasso.load(url)
    .circle()
    .into(imageView)

// multiple transformations are also supported
picasso.load(url)
    .circle()
    .grayscale()
    .into(imageView)
```

### Available transformations
|              |                                                         Transformations             |
|--------------|-------------------------------------------------------------------------------------|
| crop square  | `square()`                                                                          |
| crop circle  | `circle()`                                                                          |
| crop rounded | `rounded(radius)`<br> `rounded(radius, margin)<br> rounded(radius, margin, usedDp)` |
| overlay      | `overlay(color, alpha)`<br> `overlay(context, colorRes, alpha)`                     |
| grayscale    | `grayscale()`                                                                       |
