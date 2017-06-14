package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropSquareTransformer extends Transformer {

    private static final String TAG = "CropSquareTransformer";

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

    @NonNull
    @Override
    protected Bundle keyBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, TAG);
        return bundle;
    }
}