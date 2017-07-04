package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ColorOverlayTransformer extends Transformer {

    @ColorInt private final int color;

    ColorOverlayTransformer(@ColorInt int color) {
        this.color = color;
    }

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean shouldRecycle) {
        Bitmap target = createDefaultBitmap(source);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP));
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
        bundle.putString(KEY_NAME, "ColorOverlayTransformer");
        bundle.putInt("color", color);
        return bundle;
    }
}