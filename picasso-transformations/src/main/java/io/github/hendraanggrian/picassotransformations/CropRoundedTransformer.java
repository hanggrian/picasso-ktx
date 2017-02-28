package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropRoundedTransformer extends Transformer {

    private final int radius;
    private final int margin;

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

    @Override
    public String key() {
        return Key.newInstance(this).put("radius", radius).put("margin", margin).toString();
    }
}