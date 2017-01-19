package io.github.hendraanggrian.picassotransformations.crop;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import io.github.hendraanggrian.picassotransformations.Transformer;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CropRoundedTransformer extends Transformer {

    public static final String TAG = "rounded";
    public static final String NAME_RADIUS = "radius";
    public static final String NAME_MARGIN = "margin";

    private int radius;
    private int margin;

    public CropRoundedTransformer(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final float right = source.getWidth() - margin;
        final float bottom = source.getHeight() - margin;
        final Bitmap target = createDefaultBitmap(source);
        new Canvas(target).drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP))
                .build());
        source.recycle();
        return target;
    }

    @Override
    public String key() {
        return Key.fromTag(TAG).put(NAME_RADIUS, radius).put(NAME_MARGIN, margin).toString();
    }
}