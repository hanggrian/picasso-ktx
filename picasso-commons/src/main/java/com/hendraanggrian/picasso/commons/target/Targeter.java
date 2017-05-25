package com.hendraanggrian.picasso.commons.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class Targeter implements Target {

    @Nullable private Callback callback;

    @Override
    @CallSuper
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    @CallSuper
    public void onBitmapFailed(Drawable errorDrawable) {
        if (callback != null)
            callback.onError();
    }

    @NonNull
    public Targeter callback(@Nullable Targets.OnSuccess onSuccess) {
        return callback(onSuccess, null);
    }

    @NonNull
    public Targeter callback(@Nullable final Targets.OnSuccess onSuccess, @Nullable final Targets.OnError onError) {
        return callback(new Callback() {
            @Override
            public void onSuccess() {
                if (onSuccess != null)
                    onSuccess.onSuccess();
            }

            @Override
            public void onError() {
                if (onError != null)
                    onError.onError();
            }
        });
    }

    @NonNull
    public Targeter callback(@Nullable Callback callback) {
        this.callback = callback;
        return this;
    }
}