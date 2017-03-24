package io.github.hendraanggrian.picasso.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ImagesTarget extends MultipleTarget<Iterable<ImageView>> {

    ImagesTarget(@NonNull Iterable<ImageView> imageViews) {
        super(imageViews);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        for (ImageView imageView : getTarget())
            imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        for (ImageView imageView : getTarget())
            imageView.setImageDrawable(errorDrawable);
    }
}