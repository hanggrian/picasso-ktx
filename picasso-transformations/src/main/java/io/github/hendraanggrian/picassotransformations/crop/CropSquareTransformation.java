package io.github.hendraanggrian.picassotransformations.crop;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.BaseTransformation;
import io.github.hendraanggrian.picassotransformations.Key;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CropSquareTransformation extends BaseTransformation {

    public static final String TAG = "square";

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, int height, int width, @NonNull Bitmap target) {
        if (source != target)
            source.recycle();
        return target;
    }

    @NonNull
    @Override
    public Bitmap target(@NonNull Bitmap source) {
        final int size = Math.min(source.getWidth(), source.getHeight());
        return Bitmap.createBitmap(source,
                (source.getWidth() - size) / 2,
                (source.getHeight() - size) / 2,
                size,
                size);
    }

    @NonNull
    @Override
    public Key getKey() {
        return Key.fromTag(TAG);
    }
}