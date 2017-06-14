package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ColorGrayscaleTransformer extends Transformer {

    private static final String TAG = "ColorGrayscaleTransformer";

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean recycleSource) {
        final ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        final Bitmap target = createDefaultBitmap(source);
        new Canvas(target).drawBitmap(source, 0, 0, new PaintBuilder()
                .colorFilter(new ColorMatrixColorFilter(saturation))
                .build());
        if (recycleSource)
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