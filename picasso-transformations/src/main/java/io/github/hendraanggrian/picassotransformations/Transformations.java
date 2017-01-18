package io.github.hendraanggrian.picassotransformations;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.picasso.Transformation;

import java.util.Arrays;
import java.util.WeakHashMap;

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

    @NonNull
    public synchronized static Transformation cropSquare() {
        return get(CropSquareTransformation.TAG);
    }

    @NonNull
    public synchronized static Transformation cropCircle() {
        return get(CropCircleTransformation.TAG);
    }

    @NonNull
    public synchronized static Transformation cropRounded(int radius, int margin) {
        return cropRounded(radius, margin, false);
    }

    @NonNull
    public synchronized static Transformation cropRounded(int radius, int margin, boolean useDp) {
        return useDp
                ? get(CropRoundedTransformation.TAG, (int) (radius * Resources.getSystem().getDisplayMetrics().density), (int) (margin * Resources.getSystem().getDisplayMetrics().density))
                : get(CropRoundedTransformation.TAG, radius, margin);
    }

    @NonNull
    public synchronized static Transformation grayscale() {
        return get(GrayscaleTransformation.TAG);
    }

    @NonNull
    private synchronized static Transformation get(@NonNull String name, @NonNull Object... values) {
        if (TRANSFORMATIONS == null) {
            if (DEBUG)
                Log.d(TAG, "Initializing...");
            TRANSFORMATIONS = new WeakHashMap<>();
        }

        final String key = BaseTransformation.buildKey(name, Arrays.asList(values));
        if (TRANSFORMATIONS.containsKey(key)) {
            if (DEBUG)
                Log.d(TAG, key + " is available.");
            return TRANSFORMATIONS.get(key);
        } else {
            if (DEBUG)
                Log.d(TAG, key + " unavailable, new instance cached.");
            final Transformation transformation = BaseTransformation.parse(key);
            TRANSFORMATIONS.put(key, transformation);
            return transformation;
        }
    }
}