![logo](/art/logo.png) Picasso Extensions
=========================================
Image manipulation with pre-loaded transformations and target placeholder.

Download
--------
Library are hosted in [jCenter](https://bintray.com/hendraanggrian/maven/picasso-extensions).

```gradle
compile 'io.github.hendraanggrian:picasso-extensions:0.6.2'
```

Target
------
![demo_target](/art/demo_target.gif)

Automatically put progress bar while loading image.

```java
Picasso.with(context)
    .load(url)
    .target(Targets.progress(imageView));

// some options
Targets.progress(imageView, true); // to animate
Targets.progress(imageView).callback(callback); // to listen to Picasso events, may be Callback or Target.
```

Transformation
--------------
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

### Currently available transformations

#### Square
`Transformations.square();`

#### Circle
`Transformations.circle();`

#### Rounded
`Transformations.rounded(int radius, int margin);` to transform to rounded corners in px,
or use `Transformations.rounded(int radius, int margin, boolean useDp);` to use dp.

#### Overlay
`Transformations.overlay(int color, int alpha);` to apply color overlay,
or use `Transformations.cropRounded(Context context, int colorRes, int alpha);` to use color from resources.

#### Grayscale
`Transformations.grayscale();`
