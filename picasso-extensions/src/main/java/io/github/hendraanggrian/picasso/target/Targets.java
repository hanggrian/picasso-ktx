package io.github.hendraanggrian.picasso.target;

import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Target;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Targets {

    @NonNull
    public static Target custom(@NonNull ImageView imageView, @NonNull View view) {
        return new PlaceholderTarget(imageView, view);
    }

    @NonNull
    public static Target progress(@NonNull ImageView imageView) {
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;

        ProgressBar progressBar = new ProgressBar(imageView.getContext());
        progressBar.setLayoutParams(layoutParams);

        return new PlaceholderTarget(imageView, progressBar);
    }
}