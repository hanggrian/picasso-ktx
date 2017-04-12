package com.hendraanggrian.picasso.commons.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ImageTargeter extends SingleTargeter<ImageView> {

    ImageTargeter(@NonNull ImageView view) {
        super(view);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        getTarget().setImageBitmap(bitmap);
        super.onBitmapLoaded(bitmap, from);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        getTarget().setImageDrawable(errorDrawable);
        super.onBitmapFailed(errorDrawable);
    }
}