package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropRoundedTransformer extends Transformer {

    private final int margin;
    private final int radius;

    CropRoundedTransformer(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean shouldRecycle) {
        float right = source.getWidth() - margin;
        float bottom = source.getHeight() - margin;
        Bitmap target = createDefaultBitmap(source);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        new Canvas(target).drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, paint);

        if (shouldRecycle) {
            source.recycle();
        }
        return target;
    }

    @NonNull
    @Override
    protected Bundle keyBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, "CropRoundedTransformer");
        bundle.putInt("margin", margin);
        bundle.putInt("radius", radius);
        return bundle;
    }
}