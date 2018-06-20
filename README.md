Pikasso
=======
[![Download](https://api.bintray.com/packages/hendraanggrian/maven/pikasso/images/download.svg) ](https://bintray.com/hendraanggrian/maven/pikasso/_latestVersion)
[![Build Status](https://travis-ci.org/hendraanggrian/pikasso.svg)](https://travis-ci.org/hendraanggrian/pikasso)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Handy extension to Picasso with pre-loaded transformations and target placeholder.

Consists of several parts:
 * *Pikasso Commons*: .
 * *Pikasso Transformations*: .
 * *Pikasso Placeholders*: .
 * *Pikasso Palette*: .

Download
--------
All artifacts are hosted on [jcenter].
To download all of them, use main library:

```gradle
dependencies {
    compile 'com.hendraanggrian.pikasso:pikasso:$version'
}
```

Or download separate library if only specific feature is desired:

```gradle
dependencies {
    compile "com.hendraanggrian.pikasso:pikasso-commons:$version"
    compile "com.hendraanggrian.pikasso:pikasso-transformations:$version"
    compile "com.hendraanggrian.pikasso:pikasso-placeholders:$version"
    compile "com.hendraanggrian.pikasso:pikasso-palette:$version" 
}
```

Pikasso Commons
---------------
Call `picasso` to get global instance of `Picasso`.
Or buildPicasso to invoke Picasso.Builder.

```kotlin
picasso.load(url).into(imageView)

val myPicasso = buildPicasso {
    loggingEnabled(true)
    memoryCache(Cache.NONE)
    listener { picasso, uri, exception ->

    }
}
myPicasso.load(url).into(imageView)
```

Clean declaration of `Callback` when loading images into `ImageView`. 

```kotlin
picasso(url).into(imageView) {
    onSuccess {
        celebrate()
    }
}
```

Or into `Target` with Kotlin DSL.

```kotlin
picasso(url).into {
    onLoaded { bitmap, from ->
        process(bitmap)
    }
}
```

Pikasso Transformations
-----------------------
![demo_transformation][demo_transformation]

```kotlin
// single transformation
picasso(url)
    .circle()
    .into(imageView)

// multiple transformation
picasso(url)
    .transform(listOf(CropCircleTransformation(), ColorGrayscaleTransformation()))
    .into(imageView)
```

#### Available transformations
|              |                                                         Transformations             |
|--------------|-------------------------------------------------------------------------------------|
| crop square  | `square()`                                                                          |
| crop circle  | `circle()`                                                                          |
| crop rounded | `rounded(radius)`<br> `rounded(radius, margin)<br> rounded(radius, margin, usedDp)` |
| overlay      | `overlay(color, alpha)`<br> `overlay(context, colorRes, alpha)`                     |
| grayscale    | `grayscale()`                                                                       |

Pikasso Placeholders
--------------------
![demo_target][demo_target]

Load `ImageView` with progress bar or custom view placeholder.

```java
picasso(url).into(imageView.toProgressTarget());
```

#### Placeholder type
Display a temporary view that will be removed once Picasso has finished/failed to load the image.
 * `into(imageView.toProgressTarget())` - progress bar placeholder
 * `into(imageView.toHorizontalProgressTarget())` - horizontal progress placeholder
 * `into(imageView.toTarget(customView))` - custom view placeholder

#### Listen to events
Not yet supported.

#### Disable animation
By default, animation are enabled (if not yet already enabled) by `LayoutTransition`.
If this is not the expected behavior,
manually disable it by calling `imageView.toProgressTarget().transition(false)`.

Pikasso Palette
---------------


License
-------
    Copyright 2018 Hendra Anggrian

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[jcenter]: https://bintray.com/hendraanggrian/pikasso
[demo_target]: /art/demo_target.gif
[demo_transformation]: /art/demo_transformation.gif