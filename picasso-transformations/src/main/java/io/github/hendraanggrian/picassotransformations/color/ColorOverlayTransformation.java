package io.github.hendraanggrian.picassotransformations.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.BaseTransformation;
import io.github.hendraanggrian.picassotransformations.Key;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ColorOverlayTransformation extends BaseTransformation {

    public static final String TAG = "overlay";
    public static final String NAME_COLOR = "color";

    private int color;

    public ColorOverlayTransformation(@ColorInt int color) {
        this.color = color;
    }

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, int height, int width, @NonNull Bitmap target) {
        new Canvas(target).drawBitmap(source, 0, 0, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .colorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP))
                .build());
        source.recycle();
        return target;
    }

    @NonNull
    @Override
    public Key getKey() {
        return Key.fromTag(TAG).put(NAME_COLOR, color);
    }
}