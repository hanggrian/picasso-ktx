![logo](/art/logo.png) Picasso Transformations
==============================================
Image transformations with Picasso. It uses `WeakHashMap` to cache transformations for faster reuse.

![demo](/art/demo.gif)

Download
--------
```gradle
compile 'io.github.hendraanggrian:picasso-transformations:0.3.0@aar'
```

Usage
-----
```java
Picasso.with(context)
    .load(image)
    .transform(Transformations.circle())
    .into(target);
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