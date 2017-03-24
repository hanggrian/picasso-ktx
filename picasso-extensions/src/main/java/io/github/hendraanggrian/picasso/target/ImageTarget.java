package io.github.hendraanggrian.picasso.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ImageTarget extends SingleTarget<ImageView> {

    ImageTarget(@NonNull ImageView view) {
        super(view);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        getTarget().setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        getTarget().setImageDrawable(errorDrawable);
    }
}