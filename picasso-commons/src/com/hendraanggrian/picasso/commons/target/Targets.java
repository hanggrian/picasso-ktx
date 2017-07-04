package com.hendraanggrian.picasso.commons.target;

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

    private Targets() {
    }

    /**
     * Set default progress bar as target's placeholder.
     */
    @NonNull
    public static Targeter placeholder(@NonNull ImageView target) {
        return placeholder(target, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * Set progress bar with defined width and height as target's placeholder.
     */
    @NonNull
    public static Targeter placeholder(@NonNull ImageView target, int progressBarWidth, int progressBarHeight) {
        ProgressBar progressBar = new ProgressBar(target.getContext());
        progressBar.setLayoutParams(new FrameLayout.LayoutParams(progressBarWidth, progressBarHeight));
        ((FrameLayout.LayoutParams) progressBar.getLayoutParams()).gravity = Gravity.CENTER;
        return placeholder(target, progressBar);
    }

    /**
     * Set custom view as target's placeholder.
     */
    @NonNull
    public static Targeter placeholder(@NonNull ImageView target, @NonNull View placeholder) {
        return new PlaceholderTargeter(target, placeholder);
    }
}