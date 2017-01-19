![logo](/art/logo.png) Picasso Transformations
==============================================
Image transformations with Picasso. It uses `WeakHashMap` to cache transformations for faster reuse.
In addition to transform image with Picasso, it can also be used to transform Bitmap to Drawable and vice-versa.

![demo](/art/demo.gif)

Download
--------
```gradle
compile 'io.github.hendraanggrian:picasso-transformations:0.4.0@aar'
```

Usage
-----
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
Bitmap bitmap = Transformations.square().transform(this, R.drawable.ic_launcher);
Drawable drawable = Transformations.overlay(this, R.color.colorAccent).transformDrawable(this, R.drawable.ic_launcher);
```


Currently Available Transformations
-----------------------------------
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