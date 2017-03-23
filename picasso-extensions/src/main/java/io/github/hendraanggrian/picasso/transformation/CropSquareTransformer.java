package io.github.hendraanggrian.picasso.transformation;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropSquareTransformer extends Transformer {

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean recycleSource) {
        final int size = Math.min(source.getWidth(), source.getHeight());
        final Bitmap target = Bitmap.createBitmap(source,
                (source.getWidth() - size) / 2,
                (source.getHeight() - size) / 2,
                size,
                size);
        if (source != target && recycleSource)
            source.recycle();
        return target;
    }

    @Override
    public String key() {
        return Key.newInstance(this).toString();
    }
}