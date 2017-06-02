package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

import com.hendraanggrian.bundler.BindExtra;
import com.hendraanggrian.bundler.Bundler;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ColorOverlayTransformer extends Transformer {

    @BindExtra @ColorInt int color;

    ColorOverlayTransformer(@ColorInt int color) {
        this.color = color;
    }

    ColorOverlayTransformer(@ColorInt int color, @IntRange(from = 0x0, to = 0xFF) int alpha) {
        if (alpha < 0 || alpha > 255)
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        this.color = (color & 0x00ffffff) | (alpha << 24);
    }

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean recycleSource) {
        final Bitmap target = createDefaultBitmap(source);
        new Canvas(target).drawBitmap(source, 0, 0, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .colorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP))
                .build());
        if (recycleSource)
            source.recycle();
        return target;
    }

    @NonNull
    @Override
    protected Bundle keyBundle() {
        return Bundler.wrap(getClass(), color);
    }
}