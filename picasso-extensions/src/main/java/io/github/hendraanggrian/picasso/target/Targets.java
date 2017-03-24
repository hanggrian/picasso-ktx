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

    public static final int SCALE_DEFAULT = 0;
    public static final int SCALE_CENTER_INSIDE = 1;

    @NonNull
    public static Targeter background(@NonNull View view) {
        return background(view, SCALE_DEFAULT);
    }

    @NonNull
    public static Targeter background(@NonNull View view, int scale) {
        return new BackgroundTargeter(view, scale);
    }

    @NonNull
    public static Targeter progress(@NonNull ImageView imageView) {
        return progress(imageView, false);
    }

    @NonNull
    public static Targeter progress(@NonNull ImageView imageView, boolean animate) {
        ProgressBar progressBar = new ProgressBar(imageView.getContext());
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ((FrameLayout.LayoutParams) progressBar.getLayoutParams()).gravity = Gravity.CENTER;
        return new PlaceholderTargeter(imageView, progressBar, animate);
    }

    @NonNull
    public static Targeter customPlaceholder(@NonNull ImageView imageView, @NonNull View view) {
        return customPlaceholder(imageView, view, false);
    }

    @NonNull
    public static Targeter customPlaceholder(@NonNull ImageView imageView, @NonNull View view, boolean animate) {
        return new PlaceholderTargeter(imageView, view, animate);
    }
}