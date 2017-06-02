package com.hendraanggrian.picasso.commons.target;

import android.animation.LayoutTransition;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hendraanggrian.support.utils.view.ViewGroups;
import com.hendraanggrian.support.utils.view.Views;
import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class PlaceholderTargeter extends Targeter {

    private static final String TAG = "com.hendraanggrian.picasso.commons.target.PlaceholderTargeter";

    @NonNull private final ViewGroup parent;
    @NonNull private final ImageView target;
    private int targetPosition;
    @NonNull private final ViewGroup placeholderContainer;
    @Nullable private ImageView placeholder;

    PlaceholderTargeter(@NonNull ImageView target, @NonNull View placeholder) {
        this.parent = (ViewGroup) target.getParent();
        this.target = target;
        this.target.setTag(this);
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (parent.getChildAt(i) == target) {
                targetPosition = i;
                break;
            }
        }
        this.placeholderContainer = new FrameLayout(target.getContext());
        this.placeholderContainer.setLayoutParams(target.getLayoutParams());
        this.placeholderContainer.addView(placeholder);
        this.placeholderContainer.setTag(TAG);
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
        if (placeholder != null) {
            ViewGroups.removeViews(placeholderContainer, placeholder);
            placeholder = null;
        }
        removePlaceholders();
        target.setVisibility(View.VISIBLE);
        target.setImageBitmap(bitmap);
        super.onBitmapLoaded(bitmap, from);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (placeholder != null) {
            ViewGroups.removeViews(placeholderContainer, placeholder);
            placeholder = null;
        }
        removePlaceholders();
        target.setVisibility(View.VISIBLE);
        target.setImageDrawable(errorDrawable);
        super.onBitmapFailed(errorDrawable);
    }

    @Override
    public final void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
            placeholder = new ImageView(target.getContext());
            placeholder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            placeholder.setImageDrawable(placeHolderDrawable);
            placeholderContainer.addView(placeholder, 0);
        }
        removePlaceholders();
        parent.addView(placeholderContainer, targetPosition);
        target.setVisibility(View.GONE);
        super.onPrepareLoad(placeHolderDrawable);
    }

    @NonNull
    public PlaceholderTargeter transition(boolean enable) {
        ViewGroup parent = Views.getParent(target);
        if (parent != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            if (enable && parent.getLayoutTransition() == null)
                parent.setLayoutTransition(new LayoutTransition());
            else if (!enable)
                parent.setLayoutTransition(null);
        return this;
    }

    private void removePlaceholders() {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            Object tag = child.getTag();
            if (tag != null && tag.equals(TAG)) {
                parent.removeView(child);
            }
        }
    }
}