package io.github.hendraanggrian.picasso.target;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Targets {

    @NonNull
    public static PlaceholderTarget custom(@NonNull ImageView imageView, @NonNull View view) {
        return custom(imageView, view, false);
    }

    @NonNull
    public static PlaceholderTarget custom(@NonNull ImageView imageView, @NonNull View view, boolean animate) {
        return new PlaceholderTarget(imageView, view, animate);
    }

    @NonNull
    public static PlaceholderTarget progress(@NonNull ImageView imageView) {
        return progress(imageView, false);
    }

    @NonNull
    public static PlaceholderTarget progress(@NonNull ImageView imageView, boolean animate) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        ProgressBar progressBar = new ProgressBar(imageView.getContext());
        progressBar.setLayoutParams(layoutParams);

        return new PlaceholderTarget(imageView, progressBar, animate);
    }
}