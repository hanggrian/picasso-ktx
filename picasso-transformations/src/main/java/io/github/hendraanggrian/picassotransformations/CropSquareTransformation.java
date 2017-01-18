package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;

import com.squareup.picasso.Transformation;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropSquareTransformation implements Transformation {

    static final String TAG = "cropSquare()";

    int size;

    @Override
    public Bitmap transform(Bitmap source) {
        size = Math.min(source.getWidth(), source.getHeight());
        final int width = (source.getWidth() - size) / 2;
        final int height = (source.getHeight() - size) / 2;

        final Bitmap bitmap = Bitmap.createBitmap(source, width, height, size, size);
        if (bitmap != source)
            source.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return TAG;
    }
}