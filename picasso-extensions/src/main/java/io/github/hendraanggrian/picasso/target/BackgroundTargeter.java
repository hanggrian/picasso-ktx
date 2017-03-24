package io.github.hendraanggrian.picasso.target;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class BackgroundTargeter extends Targeter {

    @NonNull private final View view;
    @NonNull private final Scale scale;

    BackgroundTargeter(@NonNull View view, int scale) {
        this.view = view;
        this.scale = Scale.valueOf(scale);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        Bitmap manipulated = scale.manipulate(bitmap, view.getWidth(), view.getHeight());
        ViewCompat.setBackground(view, new BitmapDrawable(Resources.getSystem(), manipulated));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        ViewCompat.setBackground(view, errorDrawable);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        super.onPrepareLoad(placeHolderDrawable);
        ViewCompat.setBackground(view, placeHolderDrawable);
    }

    private enum Scale {
        DEFAULT(0) {
            @NonNull
            @Override
            Bitmap manipulate(@NonNull Bitmap original, int viewWidth, int viewHeight) {
                return original;
            }
        },
        CENTER_INSIDE(1) {
            @NonNull
            @Override
            Bitmap manipulate(@NonNull Bitmap original, int viewWidth, int viewHeight) {
                Bitmap manipulated = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);

                Canvas canvas = new Canvas(manipulated);

                float scale = viewWidth / (float) original.getWidth();
                float xTranslation = 0.0f;
                float yTranslation = (viewHeight - (float) original.getHeight() * scale) / 2.0f;

                Matrix transformation = new Matrix();
                transformation.postTranslate(xTranslation, yTranslation);
                transformation.preScale(scale, scale);

                Paint paint = new Paint();
                paint.setFilterBitmap(true);

                canvas.drawBitmap(original, transformation, paint);

                return manipulated;
            }
        };

        @NonNull
        abstract Bitmap manipulate(@NonNull Bitmap original, int viewWidth, int viewHeight);

        private final int code;

        Scale(int code) {
            this.code = code;
        }

        @NonNull
        static Scale valueOf(int code) {
            for (Scale scale : values())
                if (scale.code == code)
                    return scale;
            throw new RuntimeException();
        }
    }
}