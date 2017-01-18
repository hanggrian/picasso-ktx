package io.github.hendraanggrian.picassotransformations;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.squareup.picasso.Transformation;

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

    public synchronized static Transformation cropSquare() {
        return get(CropSquareTransformation.TAG);
    }

    public synchronized static Transformation cropCircle() {
        return get(CropCircleTransformation.TAG);
    }

    public synchronized static Transformation cropRounded(int radius, int margin) {
        return cropRounded(radius, margin, false);
    }

    public synchronized static Transformation cropRounded(int radius, int margin, boolean useDp) {
        return useDp
                ? get(CropRoundedTransformation.TAG, (int) (radius * Resources.getSystem().getDisplayMetrics().density), (int) (margin * Resources.getSystem().getDisplayMetrics().density))
                : get(CropRoundedTransformation.TAG, radius, margin);
    }

    public synchronized static Transformation grayscale() {
        return get(GrayscaleTransformation.TAG);
    }

    public synchronized static boolean isAvailable(String tag){
        return TRANSFORMATIONS.containsKey(tag);
    }

    private synchronized static Transformation get(@NonNull String tag, @NonNull Object... params) {
        if (TRANSFORMATIONS == null) {
            if (DEBUG)
                Log.d(TAG, "Initializing...");
            TRANSFORMATIONS = new WeakHashMap<>();
        }

        final String key = params.length != 0
                ? String.format(tag, params)
                : tag;

        if (TRANSFORMATIONS.containsKey(key)) {
            if (DEBUG)
                Log.d(TAG, String.format("%s is available.", key));
            return TRANSFORMATIONS.get(key);
        }

        if (DEBUG)
            Log.d(TAG, String.format("%s unavailable, creating...", key));
        switch (tag) {
            case CropSquareTransformation.TAG:
                TRANSFORMATIONS.put(key, new CropSquareTransformation());
                break;
            case CropCircleTransformation.TAG:
                TRANSFORMATIONS.put(key, new CropCircleTransformation());
                break;
            case CropRoundedTransformation.TAG:
                TRANSFORMATIONS.put(key, new CropRoundedTransformation((int) params[0], (int) params[1]));
                break;
            case GrayscaleTransformation.TAG:
                TRANSFORMATIONS.put(key, new GrayscaleTransformation());
                break;
        }
        return get(key);
    }
}