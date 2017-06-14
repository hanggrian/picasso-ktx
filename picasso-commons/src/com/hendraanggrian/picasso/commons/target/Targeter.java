package com.hendraanggrian.picasso.commons.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class Targeter implements Target {

    @Nullable private Target callback;

    @Override
    @CallSuper
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        if (callback != null) {
            callback.onBitmapLoaded(bitmap, from);
        }
    }

    @Override
    @CallSuper
    public void onBitmapFailed(Drawable errorDrawable) {
        if (callback != null) {
            callback.onBitmapFailed(errorDrawable);
        }
    }

    @Override
    @CallSuper
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (callback != null) {
            callback.onPrepareLoad(placeHolderDrawable);
        }
    }

    @NonNull
    public Targeter callback(@Nullable OnTargetLoaded loaded) {
        return callback(loaded, null);
    }

    @NonNull
    public Targeter callback(@Nullable OnTargetLoaded loaded, @Nullable OnTargetFailed failed) {
        return callback(loaded, failed, null);
    }

    @NonNull
    public Targeter callback(@Nullable final OnTargetLoaded loaded, @Nullable final OnTargetFailed failed, @Nullable final OnTargetPrepare prepare) {
        return callback(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                if (loaded != null) {
                    loaded.onBitmapLoaded(bitmap, from);
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                if (failed != null) {
                    failed.onBitmapFailed(errorDrawable);
                }
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (prepare != null) {
                    prepare.onPrepareLoad(placeHolderDrawable);
                }
            }
        });
    }

    @NonNull
    public Targeter callback(@Nullable Target callback) {
        this.callback = callback;
        return this;
    }
}