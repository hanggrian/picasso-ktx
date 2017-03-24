package io.github.hendraanggrian.picasso.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class Targeter implements Target {

    @Nullable private Object callback;

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
    public Targeter callback(@NonNull Target callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    public Targeter callback(@NonNull Callback callback) {
        this.callback = callback;
        return this;
    }
}