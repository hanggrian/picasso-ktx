package io.github.hendraanggrian.picasso.target;

import android.animation.LayoutTransition;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class PlaceholderTarget implements Target {

    @NonNull private final ImageView imageView;
    @NonNull private final ViewGroup placeholderContainer;
    @Nullable private Object callback;

    PlaceholderTarget(@NonNull ImageView imageView, @NonNull View placeholderView, boolean animate) {
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
        ViewGroupUtils.replaceView(placeholderContainer, imageView);
        imageView.setImageBitmap(bitmap);

        if (callback != null && callback instanceof Target)
            ((Target) callback).onBitmapLoaded(bitmap, from);
        else if (callback instanceof Callback)
            ((Callback) callback).onSuccess();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ViewGroupUtils.replaceView(placeholderContainer, imageView);
        imageView.setImageDrawable(errorDrawable);

        if (callback != null && callback instanceof Target)
            ((Target) callback).onBitmapFailed(errorDrawable);
        else if (callback instanceof Callback)
            ((Callback) callback).onError();
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        if (placeHolderDrawable != null) {
            ImageView imageView = new ImageView(this.imageView.getContext());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            imageView.setImageDrawable(placeHolderDrawable);
            placeholderContainer.addView(imageView, 0);
        }
        ViewGroupUtils.replaceView(imageView, placeholderContainer);

        if (callback != null && callback instanceof Target)
            ((Target) callback).onPrepareLoad(placeHolderDrawable);
    }

    @NonNull
    public PlaceholderTarget callback(@NonNull Target callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    public PlaceholderTarget callback(@NonNull Callback callback) {
        this.callback = callback;
        return this;
    }
}