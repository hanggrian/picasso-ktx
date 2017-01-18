package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropCircleTransformation extends CropSquareTransformation {

    static final String TAG = "cropCircle()";

    @Override
    public Bitmap transform(Bitmap source) {
        final Bitmap squaredBitmap = super.transform(source);
        final Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);

        final float r = size / 2f;
        new Canvas(bitmap).drawCircle(r, r, r, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(squaredBitmap)
                .build());

        squaredBitmap.recycle();
        return bitmap;
    }

    @Override
    public String key() {
        return TAG;
    }
}