package io.github.hendraanggrian.picassotransformations.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.BaseTransformation;
import io.github.hendraanggrian.picassotransformations.Key;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ColorGrayscaleTransformation extends BaseTransformation {

    public static final String TAG = "grayscale";

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, int height, int width, @NonNull Bitmap target) {
        final ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        new Canvas(target).drawBitmap(source, 0, 0, new PaintBuilder()
                .colorFilter(new ColorMatrixColorFilter(saturation))
                .build());
        source.recycle();
        return target;
    }

    @NonNull
    @Override
    public Key getKey() {
        return Key.fromTag(TAG);
    }
}