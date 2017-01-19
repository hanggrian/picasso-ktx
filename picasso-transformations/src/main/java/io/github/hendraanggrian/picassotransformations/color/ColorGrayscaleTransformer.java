package io.github.hendraanggrian.picassotransformations.color;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.Transformer;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ColorGrayscaleTransformer extends Transformer {

    public static final String TAG = "grayscale";

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean recycleSource) {
        final ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        final Bitmap target = createDefaultBitmap(source);
        new Canvas(target).drawBitmap(source, 0, 0, new PaintBuilder()
                .colorFilter(new ColorMatrixColorFilter(saturation))
                .build());
        if (recycleSource)
            source.recycle();
        return target;
    }

    @Override
    public String key() {
        return Key.fromTag(TAG).toString();
    }
}