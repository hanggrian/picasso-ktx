package com.hendraanggrian.picasso.commons.transformation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class MaskTransformer extends Transformer {

    @Nullable private static WeakReference<Paint> paint;

    @NonNull private final Drawable mask;

    MaskTransformer(@NonNull Drawable mask) {
        this.mask = mask;
    }

    @NonNull
    @Override
    protected Bitmap transform(@NonNull Bitmap source, boolean shouldRecycle) {
        int width = source.getWidth();
        int height = source.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(result);
        mask.setBounds(0, 0, width, height);
        mask.draw(canvas);
        canvas.drawBitmap(source, 0, 0, getPaint());

        if (shouldRecycle) {
            source.recycle();
        }
        return result;
    }

    @NonNull
    @Override
    protected Bundle keyBundle() {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_NAME, "MaskTransformer");
        return bundle;
    }

    @NonNull
    private Paint getPaint() {
        Paint instance;
        if (paint != null) {
            instance = paint.get();
            if (instance != null) {
                return instance;
            }
        }
        instance = new Paint();
        instance.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        paint = new WeakReference<>(instance);
        return instance;
    }
}