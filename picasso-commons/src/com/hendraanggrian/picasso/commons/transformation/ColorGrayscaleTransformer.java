package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ColorGrayscaleTransformer extends Transformer {

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean shouldRecycle) {
        Bitmap target = createDefaultBitmap(source);

        ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(saturation));
        new Canvas(target).drawBitmap(source, 0, 0, paint);

        if (shouldRecycle) {
            source.recycle();
        }
        return target;
    }

    @NonNull
    @Override
    protected Bundle keyBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, "ColorGrayscaleTransformer");
        return bundle;
    }
}