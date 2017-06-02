package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hendraanggrian.bundler.BindExtra;
import com.hendraanggrian.bundler.Bundler;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropRoundedTransformer extends Transformer {

    @BindExtra int margin;
    @BindExtra int radius;

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
        return Bundler.wrap(getClass(), margin, radius);
    }
}