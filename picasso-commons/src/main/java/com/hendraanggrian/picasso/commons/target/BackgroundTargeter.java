package com.hendraanggrian.picasso.commons.target;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class BackgroundTargeter extends SingleTargeter<View> {

    @NonNull private final Scale scale;

    BackgroundTargeter(int scaleCode, @NonNull View view) {
        super(view);
        this.scale = Scale.valueOf(scaleCode);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        Bitmap manipulated = scale.manipulate(bitmap, getTarget().getWidth(), getTarget().getHeight());
        ViewCompat.setBackground(getTarget(), new BitmapDrawable(Resources.getSystem(), manipulated));
        super.onBitmapLoaded(bitmap, from);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ViewCompat.setBackground(getTarget(), errorDrawable);
        super.onBitmapFailed(errorDrawable);
    }
}