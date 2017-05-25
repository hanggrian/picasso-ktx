package com.hendraanggrian.picasso.commons.target;

import android.animation.LayoutTransition;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class PlaceholderTarget extends Targeter {

    @NonNull private final ImageView target;
    @NonNull private final ViewGroup placeholderContainer;

    PlaceholderTarget(@NonNull ImageView target, @NonNull View placeholder) {
        this.target = target;
        this.target.setTag(this);
        this.placeholderContainer = new FrameLayout(target.getContext());
        this.placeholderContainer.setLayoutParams(target.getLayoutParams());
        this.placeholderContainer.addView(placeholder);
        transition(true);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof PlaceholderTarget && ((PlaceholderTarget) obj).target == target;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ViewGroupUtils.replaceView(placeholderContainer, target);
        target.setImageBitmap(bitmap);
        super.onBitmapLoaded(bitmap, from);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ViewGroupUtils.replaceView(placeholderContainer, target);
        target.setImageDrawable(errorDrawable);
        super.onBitmapFailed(errorDrawable);
    }

    @Override
    public final void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
            ImageView imageView = new ImageView(target.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageDrawable(placeHolderDrawable);
            placeholderContainer.addView(imageView, 0);
        }
        ViewGroupUtils.replaceView(target, placeholderContainer);
    }

    @NonNull
    public PlaceholderTarget transition(boolean enable) {
        ViewGroup parent = ViewGroupUtils.getParent(target);
        if (parent != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            if (enable && parent.getLayoutTransition() == null)
                parent.setLayoutTransition(new LayoutTransition());
            else if (!enable)
                parent.setLayoutTransition(null);
        return this;
    }
}