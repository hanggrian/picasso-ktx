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
class BackgroundsTarget extends MultipleTarget<Iterable<View>> {

    @NonNull private final Scale scale;

    BackgroundsTarget(int scaleCode, @NonNull Iterable<View> views) {
        super(views);
        this.scale = Scale.valueOf(scaleCode);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        for (View view : getTarget()) {
            Bitmap manipulated = scale.manipulate(bitmap, view.getWidth(), view.getHeight());
            ViewCompat.setBackground(view, new BitmapDrawable(Resources.getSystem(), manipulated));
        }
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        for (View view : getTarget())
            ViewCompat.setBackground(view, errorDrawable);
    }
}