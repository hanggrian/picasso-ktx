package io.github.hendraanggrian.picassotransformations.crop;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.Key;
import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CropCircleTransformation extends CropSquareTransformation {

    public static final String TAG = "circle";

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, int height, int width, @NonNull Bitmap target) {
        final int size = Math.min(width, height);
        final Bitmap squared = super.transform(source, height, width, target);
        final Bitmap circle = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        float r = size / 2f;
        new Canvas(circle).drawCircle(r, r, r, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP))
                .build());
        squared.recycle();
        return circle;
    }

    @NonNull
    @Override
    public Key getKey() {
        return Key.fromTag(TAG);
    }
}