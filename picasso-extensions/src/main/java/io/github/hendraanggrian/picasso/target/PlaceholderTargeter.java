package io.github.hendraanggrian.picasso.target;

import android.animation.LayoutTransition;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class PlaceholderTargeter extends Targeter {

    @NonNull private final ImageView imageView;
    @NonNull private final ViewGroup placeholderContainer;

    PlaceholderTargeter(@NonNull ImageView imageView, @NonNull View placeholderView, boolean animate) {
        this.imageView = imageView;
        this.imageView.setTag(this);
        this.placeholderContainer = new FrameLayout(imageView.getContext());
        this.placeholderContainer.setLayoutParams(imageView.getLayoutParams());
        this.placeholderContainer.addView(placeholderView);
        if (animate && imageView.getParent() instanceof ViewGroup)
            ((ViewGroup) imageView.getParent()).setLayoutTransition(new LayoutTransition());
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        super.onBitmapLoaded(bitmap, from);
        replaceView(placeholderContainer, imageView);
        imageView.setImageBitmap(bitmap);

    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        super.onBitmapFailed(errorDrawable);
        replaceView(placeholderContainer, imageView);
        imageView.setImageDrawable(errorDrawable);

    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        super.onPrepareLoad(placeHolderDrawable);
        if (placeHolderDrawable != null) {
            ImageView imageView = new ImageView(this.imageView.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageDrawable(placeHolderDrawable);
            placeholderContainer.addView(imageView, 0);
        }
        replaceView(imageView, placeholderContainer);
    }

    private static void replaceView(View currentView, View newView) {
        ViewGroup parent = (ViewGroup) currentView.getParent();
        if (parent != null) {
            int index = parent.indexOfChild(currentView);
            parent.removeView(currentView);
            parent.removeView(newView);
            parent.addView(newView, index);
        }
    }
}