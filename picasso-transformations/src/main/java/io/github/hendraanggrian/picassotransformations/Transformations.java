package io.github.hendraanggrian.picassotransformations;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;

import com.squareup.picasso.Transformation;

import java.util.WeakHashMap;

import io.github.hendraanggrian.picassotransformations.color.ColorGrayscaleTransformation;
import io.github.hendraanggrian.picassotransformations.color.ColorOverlayTransformation;
import io.github.hendraanggrian.picassotransformations.crop.CropCircleTransformation;
import io.github.hendraanggrian.picassotransformations.crop.CropRoundedTransformation;
import io.github.hendraanggrian.picassotransformations.crop.CropSquareTransformation;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Transformations {

    private static final String TAG = "Transformations";
    private static boolean DEBUG;

    private static volatile WeakHashMap<String, Transformation> TRANSFORMATIONS;

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    //region crop
    @NonNull
    public synchronized static Transformation square() {
        return get(Key.fromTag(CropSquareTransformation.TAG));
    }

    @NonNull
    public synchronized static Transformation circle() {
        return get(Key.fromTag(CropCircleTransformation.TAG));
    }

    @NonNull
    public synchronized static Transformation rounded(int radius, int margin) {
        return rounded(radius, margin, false);
    }

    @NonNull
    public synchronized static Transformation rounded(int radius, int margin, boolean useDp) {
        return useDp
                ? get(Key.fromTag(CropRoundedTransformation.TAG)
                .put("radius", (int) (radius * Resources.getSystem().getDisplayMetrics().density))
                .put("margin", (int) (margin * Resources.getSystem().getDisplayMetrics().density)))
                : get(Key.fromTag(CropRoundedTransformation.TAG)
                .put("radius", radius)
                .put("margin", margin));
    }
    //endregion

    //region color
    @NonNull
    public synchronized static Transformation overlay(@NonNull Context context, @ColorRes int colorRes) {
        return overlay(ContextCompat.getColor(context, colorRes));
    }

    @NonNull
    public synchronized static Transformation overlay(@NonNull Context context, @ColorRes int colorRes, int alpha) {
        return overlay(ContextCompat.getColor(context, colorRes), alpha);
    }

    @NonNull
    public synchronized static Transformation overlay(@ColorInt int color) {
        return get(Key.fromTag(ColorOverlayTransformation.TAG).put("color", color));
    }

    @NonNull
    public synchronized static Transformation overlay(@ColorInt int color, int alpha) {
        return get(Key.fromTag(ColorOverlayTransformation.TAG).put("color", ColorUtils.setAlphaComponent(color, alpha)));
    }

    @NonNull
    public synchronized static Transformation grayscale() {
        return get(Key.fromTag(ColorGrayscaleTransformation.TAG));
    }
    //endregion

    static void clearCache() {
        if (TRANSFORMATIONS != null)
            TRANSFORMATIONS.clear();
    }

    @NonNull
    private synchronized static Transformation get(@NonNull Key key) {
        if (TRANSFORMATIONS == null) {
            if (DEBUG)
                Log.d(TAG, "Initializing...");
            TRANSFORMATIONS = new WeakHashMap<>();
        }

        if (TRANSFORMATIONS.containsKey(key.toString())) {
            if (DEBUG)
                Log.d(TAG, key + " is available.");
            return TRANSFORMATIONS.get(key.toString());
        } else {
            if (DEBUG)
                Log.d(TAG, key + " unavailable, new instance cached.");
            final Transformation transformation = key.toTransformation();
            TRANSFORMATIONS.put(key.toString(), transformation);
            return transformation;
        }
    }
}