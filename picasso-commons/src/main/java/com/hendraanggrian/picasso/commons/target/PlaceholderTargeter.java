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

import com.hendraanggrian.support.utils.view.ViewGroups;
import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class PlaceholderTargeter extends Targeter {

    private static final String TAG = "com.hendraanggrian.picasso.commons.target.PlaceholderTargeter";

    @NonNull private final ViewGroup parent;
    @NonNull private final ImageView target;
    @NonNull private final ViewGroup placeholder;

    PlaceholderTargeter(@NonNull ImageView target, @NonNull View placeholderView) {
        this.parent = (ViewGroup) target.getParent();
        this.target = target;
        this.target.setTag(this);
        this.placeholder = new FrameLayout(target.getContext());
        this.placeholder.setLayoutParams(target.getLayoutParams());
        this.placeholder.addView(placeholderView);
        this.placeholder.setTag(TAG);
        transition(true);
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj instanceof PlaceholderTargeter && ((PlaceholderTargeter) obj).target == target;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ViewGroups.removeViews(parent, ViewGroups.findViewsWithTag(parent, TAG, false));
        target.setVisibility(View.VISIBLE);
        target.setImageBitmap(bitmap);
        super.onBitmapLoaded(bitmap, from);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ViewGroups.removeViews(parent, ViewGroups.findViewsWithTag(parent, TAG, false));
        target.setVisibility(View.VISIBLE);
        target.setImageDrawable(errorDrawable);
        super.onBitmapFailed(errorDrawable);
    }

    @Override
    public final void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
            ImageView placeholderView = new ImageView(target.getContext());
            placeholderView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            placeholderView.setImageDrawable(placeHolderDrawable);
            placeholder.addView(placeholderView, 0);
        }
        ViewGroups.removeViews(parent, ViewGroups.findViewsWithTag(parent, TAG, false));
        parent.addView(placeholder, parent.indexOfChild(target));
        target.setVisibility(View.GONE);
        super.onPrepareLoad(placeHolderDrawable);
    }

    @NonNull
    public PlaceholderTargeter transition(boolean enable) {
        ViewGroup parent = (ViewGroup) target.getParent();
        if (parent != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            if (enable && parent.getLayoutTransition() == null)
                parent.setLayoutTransition(new LayoutTransition());
            else if (!enable)
                parent.setLayoutTransition(null);
        return this;
    }
}