![logo](/art/logo.png) Picasso Extensions
=========================================
Image manipulation with pre-loaded transformations and target placeholder.

Download
--------
Library are hosted in [jCenter](https://bintray.com/hendraanggrian/maven/picasso-extensions).

```gradle
compile 'com.hendraanggrian:picasso-commons:0.8.0'
```

Targets
-------
![demo_target](/art/demo_target.gif)

Target one or more ImageView or View's background with custom placeholder, or pre-loaded ProgressBar.

```java
Picasso.with(context)
    .load(url)
    .target(Targets.image(imageView).placeholder(Targets.PLACEHOLDER_PROGRESS));
```

|          |                           ImageView's source                          |                                                                       View's background                                                                      |
|----------|-----------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------|
| single   | `Targets.image(imageView)`                                              | `Targets.background(view)`<br> `Targets.background(scaleType, view)`                                                                                             |
| multiple | `Targets.images(imageViews`)<br> `Targets.images(imageView1, imageView2)` | `Targets.backgrounds(views)<br> Targets.background(scaleType, views)`<br> `Targets.background(view1, view2)`<br> `Targets.background(scaleType, view1, view2)`<br> |

### Callback (single & multiple)
Listen to Picasso events with Targets.
 * `Targets.callback(OnBitmapLoaded, OnBitmapFailed, OnPrepareLoad)` - individual listeners mimicking Picasso Target
 * `Targets.callback(OnBitmapLoaded, OnBitmapFailed)` - only listen to success & failed
 * `Targets.callback(OnBitmapLoaded)` - only listen to success
 * `Targets.callback(com.squareup.picasso.Target)` - native Picasso's Target
 * `Targets.callback(com.squareup.picasso.Callback)` - native Picasso's Callback
 
### Placeholder (single only)
Display a temporary view that will be removed once Picasso has finished/failed to load the image.
 * `Targets.placeholder(Targets.PLACEHOLDER_PROGRESS)` - progress bar placeholder
 * `Targets.placeholder(view)` - custom view

### Disable animation (single only)
By default, animation are enabled (if not yet already enabled) by `LayoutTransition`.
If this is not the expected behavior, manually disable it by calling `Targets.disableAnimation()`.

Transformations
---------------
![demo_transformation](/art/demo_transformation.gif)

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

|              |                                                         Transformations                                                         |
|--------------|---------------------------------------------------------------------------------------------------------------------------------|
| crop square  | `Transformations.square()`                                                                                                        |
| crop circle  | `Transformations.circle()`                                                                                                        |
| crop rounded | `Transformations.rounded(radius)`<br> `Transformations.rounded(radius, margin)<br> Transformations.rounded(radius, margin, usedDp)` |
| overlay      | `Transformations.overlay(color, alpha)`<br> `Transformations.overlay(context, colorRes, alpha)`                                     |
| grayscale    | `Transformations.grayscale()`                                                                                                     |
