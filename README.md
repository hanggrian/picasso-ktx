![logo](/art/logo.png) Picasso Transformations
==============================================
Image transformations with Picasso. It uses `WeakHashMap` to cache transformations for faster reuse.

![demo](/art/demo.gif)

Download
--------
```gradle
compile 'io.github.hendraanggrian:picasso-transformations:0.2.0@aar'
```

Usage
-----
```java
Picasso.with(context)
    .load(image)
    .transform(Transformations.cropCircle())
    .into(target);
```


Currently Available Transformations
-----------------------------------
#### Crop Square
`Transformations.cropSquare();`

#### Crop Circle
`Transformations.cropCircle();`

#### Crop Rounded
`Transformations.cropRounded(int radius, int margin);` to transform to rounded corners in px,
or use `Transformations.cropRounded(int radius, int margin, boolean useDp);` to use dp.

#### Grayscale
`Transformations.grayscale();`