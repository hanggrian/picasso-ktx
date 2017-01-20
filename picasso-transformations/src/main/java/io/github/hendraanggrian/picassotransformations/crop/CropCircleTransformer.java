package io.github.hendraanggrian.picassotransformations.crop;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;

import io.github.hendraanggrian.picassotransformations.internal.PaintBuilder;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class CropCircleTransformer extends CropSquareTransformer {

    public static final String TAG = "circle";

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean recycleSource) {
        float r = Math.min(source.getWidth(), source.getHeight()) / 2f;
        final Bitmap squared = super.transform(source, recycleSource);
        final Bitmap circle = createDefaultBitmap(source);
        new Canvas(circle).drawCircle(r, r, r, new PaintBuilder(Paint.ANTI_ALIAS_FLAG)
                .shader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP))
                .build());
        if (recycleSource)
            squared.recycle();
        return circle;
    }

    @Override
    public String key() {
        return Key.fromTag(TAG).toString();
    }
}