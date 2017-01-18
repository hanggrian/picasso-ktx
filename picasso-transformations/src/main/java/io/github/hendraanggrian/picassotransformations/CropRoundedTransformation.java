package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Arrays;
import java.util.Collection;

import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropRoundedTransformation extends BaseTransformation {

    static final String TAG = "cropRounded";

    private int radius;
    private int margin;

    CropRoundedTransformation(int radius, int margin) {
        this.radius = radius;
        this.margin = margin;
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final int width = source.getWidth();
        final int height = source.getHeight();
        final Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        float right = width - margin;
        float bottom = height - margin;
        new Canvas(bitmap).drawRoundRect(new RectF(margin, margin, right, bottom), radius, radius, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(source)
                .build());

        source.recycle();
        return bitmap;
    }

    @NonNull
    @Override
    String name() {
        return TAG;
    }

    @Nullable
    @Override
    Collection values() {
        return Arrays.asList(radius, margin);
    }
}