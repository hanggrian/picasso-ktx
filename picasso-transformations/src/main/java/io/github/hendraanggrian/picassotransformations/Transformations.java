package io.github.hendraanggrian.picassotransformations;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.util.Log;

import java.util.WeakHashMap;

import io.github.hendraanggrian.picassotransformations.color.ColorGrayscaleTransformer;
import io.github.hendraanggrian.picassotransformations.color.ColorOverlayTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropCircleTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropRoundedTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropSquareTransformer;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Transformations {

    private static final String TAG = "Transformations";
    private static boolean DEBUG;

    private static volatile WeakHashMap<String, Transformer> TRANSFORMATIONS;

    public static void setDebug(boolean debug) {
        DEBUG = debug;
    }

    //region crop
    @NonNull
    public synchronized static Transformer square() {
        return get(Transformer.Key.fromTag(CropSquareTransformer.TAG));
    }

    @NonNull
    public synchronized static Transformer circle() {
        return get(Transformer.Key.fromTag(CropCircleTransformer.TAG));
    }

    @NonNull
    public synchronized static Transformer rounded(int radius, int margin) {
        return rounded(radius, margin, false);
    }

    @NonNull
    public synchronized static Transformer rounded(int radius, int margin, boolean useDp) {
        return useDp
                ? get(Transformer.Key.fromTag(CropRoundedTransformer.TAG)
                .put(CropRoundedTransformer.NAME_RADIUS, (int) (radius * Resources.getSystem().getDisplayMetrics().density))
                .put(CropRoundedTransformer.NAME_MARGIN, (int) (margin * Resources.getSystem().getDisplayMetrics().density)))
                : get(Transformer.Key.fromTag(CropRoundedTransformer.TAG)
                .put(CropRoundedTransformer.NAME_RADIUS, radius)
                .put(CropRoundedTransformer.NAME_MARGIN, margin));
    }
    //endregion

    //region color
    @NonNull
    public synchronized static Transformer overlay(@NonNull Context context, @ColorRes int colorRes) {
        return overlay(ContextCompat.getColor(context, colorRes));
    }

    @NonNull
    public synchronized static Transformer overlay(@NonNull Context context, @ColorRes int colorRes, int alpha) {
        return overlay(ContextCompat.getColor(context, colorRes), alpha);
    }

    @NonNull
    public synchronized static Transformer overlay(@ColorInt int color) {
        return get(Transformer.Key.fromTag(ColorOverlayTransformer.TAG).put(ColorOverlayTransformer.NAME_COLOR, color));
    }

    @NonNull
    public synchronized static Transformer overlay(@ColorInt int color, int alpha) {
        return get(Transformer.Key.fromTag(ColorOverlayTransformer.TAG).put(ColorOverlayTransformer.NAME_COLOR, ColorUtils.setAlphaComponent(color, alpha)));
    }

    @NonNull
    public synchronized static Transformer grayscale() {
        return get(Transformer.Key.fromTag(ColorGrayscaleTransformer.TAG));
    }
    //endregion

    static void clearCache() {
        if (TRANSFORMATIONS != null)
            TRANSFORMATIONS.clear();
    }

    @NonNull
    private synchronized static Transformer get(@NonNull Transformer.Key key) {
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
            final Transformer transformer = key.toTransformer();
            TRANSFORMATIONS.put(key.toString(), transformer);
            return transformer;
        }
    }
}