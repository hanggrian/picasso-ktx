Pikasso
=======
[![Download](https://api.bintray.com/packages/hendraanggrian/maven/pikasso/images/download.svg) ](https://bintray.com/hendraanggrian/maven/pikasso/_latestVersion)
[![Build Status](https://travis-ci.org/hendraanggrian/pikasso.svg)](https://travis-ci.org/hendraanggrian/pikasso)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)

Handy extension to Picasso with pre-loaded transformations and target placeholder.

Download
--------
```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    compile "com.hendraanggrian:pikasso:$version"
}
```

Global instance
---------------
Call `picasso` to get global instance of `Picasso`, it is equivalent to `Picasso.get()`.
This is the only root-level accessor in this library.

```kotlin
picasso.load(url).into(imageView)
```

Callback DSL
------------
Traditional:
```kotlin
Picasso.get().load(url).into(imageView, object : Callback {
    override fun onSuccess() {
        
    }
    override fun onError(e: Exception) {
        
    }
})

Picasso.get().load(url).into(object : Target {
    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
        
    }
    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        
    }
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        
    }
})
```

Pikasso:
```kotlin
picasso.load(url).into(imageView) {
    onSuccess {
        
    }
    onFailed {
    
    }
}

picasso.load(url).into {
    onFailed { e, drawable ->
    
    }
    onLoaded { bitmap, from ->
    
    }
    onPrepare { drawable ->
    
    }
}
```

Transformations
---------------
![demo_transformation][demo_transformation]

```kotlin
// single transformation
Picasso.with(context)
    .load(image)
    .circle()
    .into(target)
    
// multiple transformation
Picasso.with(context)
    .load(image)
    .transform(listOf(CropCircleTransformation(), ColorGrayscaleTransformation()))
    .into(target)
```

#### Available transformations
|              |                                                         Transformations             |
|--------------|-------------------------------------------------------------------------------------|
| crop square  | `square()`                                                                          |
| crop circle  | `circle()`                                                                          |
| crop rounded | `rounded(radius)`<br> `rounded(radius, margin)<br> rounded(radius, margin, usedDp)` |
| overlay      | `overlay(color, alpha)`<br> `overlay(context, colorRes, alpha)`                     |
| grayscale    | `grayscale()`                                                                       |

Placeholder target
------------------
![demo_target][demo_target]

Load `ImageView` with progress bar or custom view placeholder.

```java
picasso.load(url)
    .transform(Transformations.circle())
    .into(Targets.placeholder(imageView));
```
 
#### Placeholder type
Display a temporary view that will be removed once Picasso has finished/failed to load the image.
 * `into(imageView.toProgressTarget())` - progress bar placeholder
 * `into(imageView.toHorizontalProgressTarget())` - horizontal progress placeholder
 * `into(imageView.toTarget(customView))` - custom view placeholder

#### Listen to events
Listen to Picasso events with placeholder target with callback DSL.

```kotlin
into(imageView.toProgressTarget()) {
    onSuccess {
        
    }
}
```

#### Disable animation
By default, animation are enabled (if not yet already enabled) by `LayoutTransition`.
If this is not the expected behavior, 
manually disable it by calling `imageView.toProgressTarget().transition(false)`.

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
    
[demo_target]: /art/demo_target.gif
[demo_transformation]: /art/demo_transformation.gif
