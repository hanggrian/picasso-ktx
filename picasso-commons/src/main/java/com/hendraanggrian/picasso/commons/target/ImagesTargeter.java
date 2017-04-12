package com.hendraanggrian.picasso.commons.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ImagesTargeter extends MultipleTargeter<Iterable<ImageView>> {

    ImagesTargeter(@NonNull Iterable<ImageView> imageViews) {
        super(imageViews);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        for (ImageView imageView : getTarget())
            imageView.setImageBitmap(bitmap);
        super.onBitmapLoaded(bitmap, from);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        for (ImageView imageView : getTarget())
            imageView.setImageDrawable(errorDrawable);
        super.onBitmapFailed(errorDrawable);
    }
}