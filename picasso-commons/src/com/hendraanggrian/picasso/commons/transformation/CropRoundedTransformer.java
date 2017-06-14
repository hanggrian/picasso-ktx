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

    private static final String TAG = "CropRoundedTransformer";

    private int margin;
    private int radius;

    CropRoundedTransformer(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean recycleSource) {
        final float right = source.getWidth() - margin;
        final float bottom = source.getHeight() - margin;
        final Bitmap target = createDefaultBitmap(source);
        new Canvas(target).drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP))
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
        bundle.putInt("margin", margin);
        bundle.putInt("radius", radius);
        return bundle;
    }
}