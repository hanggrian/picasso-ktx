package io.github.hendraanggrian.picasso.target;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Targets {

    public static final int PLACEHOLDER_PROGRESS = 0;

    public static final int SCALE_NO_SCALING = 0;
    public static final int SCALE_CENTER_INSIDE = 1;

    @NonNull
    public static SingleTarget<ImageView> image(@NonNull ImageView imageView) {
        return new ImageTarget(imageView);
    }

    @NonNull
    public static SingleTarget<View> background(@NonNull View view) {
        return background(SCALE_NO_SCALING, view);
    }

    @NonNull
    public static SingleTarget<View> background(@ScaleCode int scale, @NonNull View view) {
        return new BackgroundTarget(scale, view);
    }

    @NonNull
    public static MultipleTarget<Iterable<ImageView>> images(@NonNull ImageView... imageViews) {
        return images(Arrays.asList(imageViews));
    }

    @NonNull
    public static MultipleTarget<Iterable<ImageView>> images(@NonNull Iterable<ImageView> imageViews) {
        return new ImagesTarget(imageViews);
    }

    @NonNull
    public static MultipleTarget<Iterable<View>> backgrounds(@NonNull View... views) {
        return backgrounds(SCALE_NO_SCALING, views);
    }

    @NonNull
    public static MultipleTarget<Iterable<View>> backgrounds(@NonNull Iterable<View> views) {
        return backgrounds(SCALE_NO_SCALING, views);
    }

    @NonNull
    public static MultipleTarget<Iterable<View>> backgrounds(@ScaleCode int scale, @NonNull View... views) {
        return backgrounds(scale, Arrays.asList(views));
    }

    @NonNull
    public static MultipleTarget<Iterable<View>> backgrounds(@ScaleCode int scale, @NonNull Iterable<View> views) {
        return new BackgroundsTarget(scale, views);
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PLACEHOLDER_PROGRESS})
    @interface PlaceholderCode {
    }

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({SCALE_NO_SCALING, SCALE_CENTER_INSIDE})
    @interface ScaleCode {
    }
}