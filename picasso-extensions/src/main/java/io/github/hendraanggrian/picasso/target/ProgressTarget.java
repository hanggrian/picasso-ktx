package io.github.hendraanggrian.picasso.target;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public class ProgressTarget implements Target {

    @NonNull private final ImageView imageView;
    @NonNull private final FrameLayout progressContainer;

    public ProgressTarget(@NonNull ImageView imageView) {
        this(imageView, new ProgressBar(imageView.getContext()));
    }

    public ProgressTarget(@NonNull ImageView imageView, @Nullable ProgressBar progressBar) {
        this.imageView = imageView;
        this.imageView.setTag(this);
        this.progressContainer = new FrameLayout(imageView.getContext());
        this.progressContainer.setLayoutParams(imageView.getLayoutParams());

        ProgressBar progress = progressBar == null
                ? new ProgressBar(imageView.getContext())
                : progressBar;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        progress.setLayoutParams(layoutParams);
        this.progressContainer.addView(progress);
    }

    @Override
    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
        ViewGroupUtils.replaceView(progressContainer, imageView);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        ViewGroupUtils.replaceView(progressContainer, imageView);
        imageView.setImageDrawable(errorDrawable);
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        ViewGroupUtils.replaceView(imageView, progressContainer);
    }
}