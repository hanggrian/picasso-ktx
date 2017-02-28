package io.github.hendraanggrian.picassotransformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Superclass of all <tt>Transformation</tt>.
 * In addition to function as <tt>Transformation</tt> for <tt>Picasso</tt>,
 * <tt>Transformer</tt> can also independently transform bitmap to bitmap, bitmap to drawable,
 * drawable to bitmap, and drawable to drawable using {@code toBitmap()} and {@code toDrawable().}
 * <p>
 * Do not explicitly trigger {@code transform()} as it will automatically recycle source bitmap,
 * {@code transform()} should only be invoked by <tt>Picasso</tt>.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class Transformer implements Transformation {

    /**
     * Logic of image transformation should happen here.
     *
     * @param source        bitmap, should not be null.
     * @param recycleSource true if source bitmap should be recycled,
     *                      which is a required behavior of {@link Transformation#transform(Bitmap)}.
     * @return transformed <tt>Bitmap</tt>.
     */
    @NonNull
    protected abstract Bitmap transform(@NonNull Bitmap source, boolean recycleSource);

    /**
     * Implemented from <tt>Transformation</tt> and should only be invoked by <tt>Picasso</tt>.
     * Calling this method outside <tt>Picasso</tt> will cause the source bitmap to be recycled and
     * likely to cause errors.
     *
     * @param source bitmap.
     * @return transformed bitmap.
     */
    @NonNull
    @Override
    public Bitmap transform(Bitmap source) {
        return transform(source, true);
    }

    /**
     * Transform source bitmap to target bitmap.
     *
     * @param source bitmap, should not be null or recycled.
     * @return bitmap, never null.
     */
    @NonNull
    public Bitmap toBitmap(@NonNull Bitmap source) {
        return transform(source, false);
    }

    /**
     * Transform source drawable to target bitmap.
     *
     * @param source drawable, should not be null.
     * @return bitmap, never null.
     */
    @NonNull
    public Bitmap toBitmap(@NonNull Drawable source) {
        return toBitmap(((BitmapDrawable) source).getBitmap());
    }

    /**
     * Transform source drawable from resources to target bitmap.
     *
     * @param context   should not be null.
     * @param sourceRes resource id of drawable.
     * @return bitmap, never null.
     */
    @NonNull
    public Bitmap toBitmap(@NonNull Context context, @DrawableRes int sourceRes) {
        return toBitmap(ContextCompat.getDrawable(context, sourceRes));
    }

    /**
     * Transform source bitmap to target drawable.
     *
     * @param context should not be null.
     * @param source  bitmap, should not be null or recycled.
     * @return drawable, never null.
     */
    @NonNull
    public Drawable toDrawable(@NonNull Context context, @NonNull Bitmap source) {
        return new BitmapDrawable(context.getResources(), toBitmap(source));
    }

    /**
     * Transform source drawable to target drawable.
     *
     * @param context should not be null.
     * @param source  drawable, should not be null.
     * @return drawable, never null.
     */
    @NonNull
    public Drawable toDrawable(@NonNull Context context, @NonNull Drawable source) {
        return new BitmapDrawable(context.getResources(), toBitmap(source));
    }

    /**
     * Transform source drawable from resources to target drawable.
     *
     * @param context   should not be null.
     * @param sourceRes resource id of drawable.
     * @return drawable, never null.
     */
    @NonNull
    public Drawable toDrawable(@NonNull Context context, @DrawableRes int sourceRes) {
        return toDrawable(context, ContextCompat.getDrawable(context, sourceRes));
    }

    /**
     * Creates new bitmap with default configuration, only used in subclasses of <tt>Transformer</tt>.
     *
     * @param source bitmap, should not be null.
     * @return empty bitmap, never null.
     */
    @NonNull
    protected Bitmap createDefaultBitmap(@NonNull Bitmap source) {
        return Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    /**
     * Json object with builder pattern that will ultimately be converted to String for
     * <tt>Picasso</tt> to use for caching purpose.
     */
    static final class Key extends JSONObject {

        private Key() {
            super();
        }

        @NonNull
        public Key put(@NonNull String name, String value) {
            try {
                super.put(name, value);
                return this;
            } catch (JSONException e) {
                throw new RuntimeException();
            }
        }

        @NonNull
        @Override
        public Key put(@NonNull String name, int value) {
            try {
                super.put(name, value);
                return this;
            } catch (JSONException e) {
                throw new RuntimeException();
            }
        }

        static Key newInstance(@NonNull Transformer transformer) {
            return new Key().put("name", transformer.getClass().getSimpleName());
        }
    }
}