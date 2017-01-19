package io.github.hendraanggrian.picassotransformations;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.squareup.picasso.Transformation;

/**
 * Superclass of all transformations that builds <tt>JSONObject</tt> as a key for <tt>Transformation</tt> caching.
 * <p>
 * Subclasses of this class should no longer override methods from <tt>Transformation</tt>
 * since abstract methods of this class are passed to <tt>Transformation</tt> methods.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class BaseTransformation implements Transformation {

    /**
     * Replacement of {@link Transformation#transform(Bitmap)}.
     *
     * @param source Bitmap.
     * @param height of source Bitmap.
     * @param width  of source Bitmap.
     * @param target new empty Bitmap configured on {@link BaseTransformation#target(Bitmap)}.
     * @return transformed Bitmap, must not null.
     */
    @NonNull
    public abstract Bitmap transform(@NonNull Bitmap source, int height, int width, @NonNull Bitmap target);

    /**
     * Replacement of {@link Transformation#key()}.
     *
     * @return key, must not null.
     */
    @NonNull
    public abstract Key getKey();

    /**
     * Implemented from <tt>Transformation</tt>.
     *
     * @return transformed bitmap.
     */
    @Override
    public Bitmap transform(Bitmap source) {
        return transform(source, source.getHeight(), source.getWidth(), target(source));
    }

    /**
     * Implemented from <tt>Transformation</tt>.
     *
     * @return key for transformation caching.
     */
    @Override
    public String key() {
        return getKey().toString();
    }

    /**
     * Construct new default Bitmap, override to use custom Bitmap as target.
     *
     * @param source Bitmap from {@link Transformation#transform(Bitmap)}.
     * @return target Bitmap to be used on {@link BaseTransformation#transform(Bitmap, int, int, Bitmap)}.
     */
    @NonNull
    public Bitmap target(@NonNull Bitmap source) {
        return Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }
}