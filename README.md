Pikasso
=======
Handy extension to Picasso with pre-loaded transformations and target placeholder.

Download
--------
```gradle
repositories {
    google()
    jcenter()
}

dependencies {
    compile 'com.hendraanggrian:pikasso:0.1'
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
```

Pikasso:
```kotlin
picasso.load(url).into(imageView) {
    onSuccess {
        
    }
    onFailed {
    
    }
}
```

Transformations
---------------
![demo_transformation][demo_transformation]

```java
// single transformation
Picasso.with(context)
    .load(image)
    .transform(Transformations.circle())
    .into(target);
    
// multiple transformation
Picasso.with(context)
    .load(image)
    .transform(Arrays.asList(Transformations.circle(), Transformations.grayscale()))
    .into(target);

// it can also be used without Picasso
Bitmap bitmap = Transformations.square().toBitmap(this, R.drawable.ic_launcher);
Drawable drawable = Transformations.overlay(this, R.color.colorAccent).toDrawable(this, R.drawable.ic_launcher);
```

#### Available transformations
|              |                                                         Transformations                                                         |
|--------------|---------------------------------------------------------------------------------------------------------------------------------|
| crop square  | `Transformations.square()`                                                                                                        |
| crop circle  | `Transformations.circle()`                                                                                                        |
| crop rounded | `Transformations.rounded(radius)`<br> `Transformations.rounded(radius, margin)<br> Transformations.rounded(radius, margin, usedDp)` |
| overlay      | `Transformations.overlay(color, alpha)`<br> `Transformations.overlay(context, colorRes, alpha)`                                     |
| grayscale    | `Transformations.grayscale()`                                                                                                     |

Targets
-------
![demo_target][demo_target]

Load `ImageView` with progress bar or custom view placeholder.

```java
picasso(url)
    .transform(Transformations.circle())
    .into(Targets.placeholder(imageView));
```
 
#### Placeholder type
Display a temporary view that will be removed once Picasso has finished/failed to load the image.
 * `Targets.placeholder(View)` - progress bar placeholder
 * `Targets.placeholder(ImageView, view)` - custom view placeholder

#### Listen to events
Listen to Picasso events with Targets.
 * `Targets.callback(Targets.OnSuccess, Targets.OnError)` - only listen to success & failed, mimicking Picasso's Callback
 * `Targets.callback(Targets.OnSuccess)` - only listen to success
 * `Targets.callback(com.squareup.picasso.Callback)` - native Picasso's Callback

#### Disable animation
By default, animation are enabled (if not yet already enabled) by `LayoutTransition`.
If this is not the expected behavior, manually disable it by calling `Targets.transition(boolean)`.

License
-------
    Copyright 2017 Hendra Anggrian

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
