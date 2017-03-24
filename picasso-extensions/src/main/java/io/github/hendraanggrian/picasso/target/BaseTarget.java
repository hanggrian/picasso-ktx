package io.github.hendraanggrian.picasso.target;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import io.github.hendraanggrian.picasso.target.callback.OnBitmapFailed;
import io.github.hendraanggrian.picasso.target.callback.OnBitmapLoaded;
import io.github.hendraanggrian.picasso.target.callback.OnPrepareLoad;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class BaseTarget<T> implements Target {

    public abstract void initTag(T target);

    @NonNull private final T target;
    @Nullable private Object callback;

    public BaseTarget(@NonNull T target) {
        this.target = target;
        initTag(target);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof BaseTarget && ((BaseTarget) obj).target == target;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (callback != null && callback instanceof Target)
            ((Target) callback).onBitmapLoaded(bitmap, from);
        else if (callback instanceof Callback)
            ((Callback) callback).onSuccess();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (callback != null && callback instanceof Target)
            ((Target) callback).onBitmapFailed(errorDrawable);
        else if (callback instanceof Callback)
            ((Callback) callback).onError();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (callback != null && callback instanceof Target)
            ((Target) callback).onPrepareLoad(placeHolderDrawable);
    }

    @NonNull
    public BaseTarget callback(@Nullable OnBitmapLoaded onBitmapLoaded) {
        return callback(onBitmapLoaded, null, null);
    }

    @NonNull
    public BaseTarget callback(@Nullable OnBitmapLoaded onBitmapLoaded, @Nullable OnBitmapFailed onBitmapFailed) {
        return callback(onBitmapLoaded, onBitmapFailed, null);
    }

    @NonNull
    public BaseTarget callback(@Nullable final OnBitmapLoaded onBitmapLoaded, @Nullable final OnBitmapFailed onBitmapFailed, @Nullable final OnPrepareLoad onPrepareLoad) {
        return callback(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (onBitmapLoaded != null)
                    onBitmapLoaded.onBitmapLoaded(bitmap, from);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (onBitmapFailed != null)
                    onBitmapFailed.onBitmapFailed(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (onPrepareLoad != null)
                    onPrepareLoad.onPrepareLoad(placeHolderDrawable);
            }
        });
    }

    @NonNull
    public BaseTarget callback(@NonNull Target callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    public BaseTarget callback(@NonNull Callback callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    protected T getTarget() {
        return target;
    }

    enum Scale {
        NO_SCALING(0) {
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