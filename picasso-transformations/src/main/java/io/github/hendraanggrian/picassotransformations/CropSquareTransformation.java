package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropSquareTransformation extends BaseTransformation {

    static final String TAG = "cropSquare";

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

    @NonNull
    @Override
    String name() {
        return TAG;
    }

    @Nullable
    @Override
    Collection values() {
        return null;
    }
}