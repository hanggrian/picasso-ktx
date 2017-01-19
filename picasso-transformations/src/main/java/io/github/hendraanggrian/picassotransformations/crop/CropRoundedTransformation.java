package io.github.hendraanggrian.picassotransformations.crop;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.BaseTransformation;
import io.github.hendraanggrian.picassotransformations.Key;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CropRoundedTransformation extends BaseTransformation {

    public static final String TAG = "rounded";
    public static final String NAME_RADIUS = "radius";
    public static final String NAME_MARGIN = "margin";

    private int radius;
    private int margin;

    public CropRoundedTransformation(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, int height, int width, @NonNull Bitmap target) {
        float right = width - margin;
        float bottom = height - margin;
        new Canvas(target).drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP))
                .build());
        source.recycle();
        return target;
    }

    @NonNull
    @Override
    public Key getKey() {
        return Key.fromTag(TAG).put(NAME_RADIUS, radius).put(NAME_MARGIN, margin);
    }
}