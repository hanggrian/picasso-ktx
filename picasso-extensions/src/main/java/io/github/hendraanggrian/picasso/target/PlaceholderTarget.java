package io.github.hendraanggrian.picasso.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class PlaceholderTarget implements Target {

    @NonNull private final ImageView imageView;
    @NonNull private final ViewGroup placeholderContainer;
    @Nullable private Callback callback;

    PlaceholderTarget(@NonNull ImageView imageView, @NonNull View placeholderView) {
        this.imageView = imageView;
        this.imageView.setTag(this);
        this.placeholderContainer = new FrameLayout(imageView.getContext());
        this.placeholderContainer.setLayoutParams(imageView.getLayoutParams());
        this.placeholderContainer.addView(placeholderView);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ViewGroupUtils.replaceView(placeholderContainer, imageView);
        imageView.setImageBitmap(bitmap);
        if (callback != null)
            callback.onSuccess();
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ViewGroupUtils.replaceView(placeholderContainer, imageView);
        imageView.setImageDrawable(errorDrawable);
        if (callback != null)
            callback.onError();
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
    }

    @NonNull
    public PlaceholderTarget callback(@NonNull Callback callback) {
        this.callback = callback;
        return this;
    }

    @NonNull
    public static PlaceholderTarget custom(@NonNull ImageView imageView, @NonNull View view) {
        return new PlaceholderTarget(imageView, view);
    }

    @NonNull
    public static PlaceholderTarget progress(@NonNull ImageView imageView) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        ProgressBar progressBar = new ProgressBar(imageView.getContext());
        progressBar.setLayoutParams(layoutParams);

        return new PlaceholderTarget(imageView, progressBar);
    }
}