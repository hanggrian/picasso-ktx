package io.github.hendraanggrian.picassotransformations.crop;

import android.graphics.Bitmap;

import io.github.hendraanggrian.picassotransformations.Transformer;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CropSquareTransformer extends Transformer {

    public static final String TAG = "square";

    @Override
    public Bitmap transform(Bitmap source) {
        final int size = Math.min(source.getWidth(), source.getHeight());
        final Bitmap target = Bitmap.createBitmap(source,
                (source.getWidth() - size) / 2,
                (source.getHeight() - size) / 2,
                size,
                size);
        if (source != target)
            source.recycle();
        return target;
    }

    @Override
    public String key() {
        return Key.fromTag(TAG).toString();
    }
}