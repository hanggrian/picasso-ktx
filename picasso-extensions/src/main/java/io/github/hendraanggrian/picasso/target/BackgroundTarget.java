package io.github.hendraanggrian.picasso.target;

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
class BackgroundTarget extends SingleTarget<View> {

    @NonNull private final Scale scale;

    BackgroundTarget(int scaleCode, @NonNull View view) {
        super(view);
        this.scale = Scale.valueOf(scaleCode);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        Bitmap manipulated = scale.manipulate(bitmap, getTarget().getWidth(), getTarget().getHeight());
        ViewCompat.setBackground(getTarget(), new BitmapDrawable(Resources.getSystem(), manipulated));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        ViewCompat.setBackground(getTarget(), errorDrawable);
    }
}