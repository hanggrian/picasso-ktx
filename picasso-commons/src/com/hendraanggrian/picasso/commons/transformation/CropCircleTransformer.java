package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class CropCircleTransformer extends CropSquareTransformer {

    @NonNull
    @Override
    public Bitmap transform(@NonNull Bitmap source, boolean shouldRecycle) {
        float r = Math.min(source.getWidth(), source.getHeight()) / 2f;
        Bitmap squared = super.transform(source, shouldRecycle);
        Bitmap circle = createDefaultBitmap(source);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        new Canvas(circle).drawCircle(r, r, r, paint);

        if (shouldRecycle) {
            squared.recycle();
        }
        return circle;
    }

    @NonNull
    @Override
    protected Bundle keyBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, "CropCircleTransformer");
        return bundle;
    }
}