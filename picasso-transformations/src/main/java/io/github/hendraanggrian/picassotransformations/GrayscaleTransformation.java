package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Collection;

import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class GrayscaleTransformation extends BaseTransformation {

    static final String TAG = "cropGrayscale";

    @Override
    public Bitmap transform(Bitmap source) {
        final Bitmap bitmap = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        final ColorMatrix saturation = new ColorMatrix();
        saturation.setSaturation(0f);
        new Canvas(bitmap).drawBitmap(source, 0, 0, new PaintBuilder()
                .colorFilter(new ColorMatrixColorFilter(saturation))
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
        return null;
    }
}