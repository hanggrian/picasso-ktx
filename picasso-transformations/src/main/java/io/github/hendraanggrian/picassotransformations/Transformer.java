package io.github.hendraanggrian.picassotransformations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;

import io.github.hendraanggrian.picassotransformations.color.ColorGrayscaleTransformer;
import io.github.hendraanggrian.picassotransformations.color.ColorOverlayTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropCircleTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropRoundedTransformer;
import io.github.hendraanggrian.picassotransformations.crop.CropSquareTransformer;

import static java.lang.annotation.RetentionPolicy.SOURCE;

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
    protected static final class Key extends JSONObject {

        private static final String NAME_TAG = "tag";

        /**
         * Key creation are only available with {@link Key#fromTag(String)}.
         */
        private Key() {
        }

        /**
         * Maps {@code name} to {@code value}.
         *
         * @param name  of this value.
         * @param value a json-supported object.
         * @return this key, never null.
         */
        @NonNull
        @Override
        public Key put(@NonNull String name, Object value) {
            try {
                super.put(name, value);
                return this;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Maps {@code name} to {@code value}.
         *
         * @param name  of this value.
         * @param value an int.
         * @return this key, never null.
         */
        @NonNull
        @Override
        public Key put(@NonNull String name, int value) {
            try {
                super.put(name, value);
                return this;
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Creates a new <tt>Transformation</tt> based on this key's properties.
         *
         * @return a <tt>Transformation</tt>, never null.
         */
        @NonNull
        Transformer toTransformer() {
            try {
                switch (getString(NAME_TAG)) {
                    //region crop
                    case CropSquareTransformer.TAG:
                        return new CropSquareTransformer();
                    case CropCircleTransformer.TAG:
                        return new CropCircleTransformer();
                    case CropRoundedTransformer.TAG:
                        return new CropRoundedTransformer(
                                getInt(CropRoundedTransformer.NAME_RADIUS),
                                getInt(CropRoundedTransformer.NAME_MARGIN));
                    //endregion
                    //region color
                    case ColorOverlayTransformer.TAG:
                        return new ColorOverlayTransformer(
                                getInt(ColorOverlayTransformer.NAME_COLOR));
                    case ColorGrayscaleTransformer.TAG:
                        return new ColorGrayscaleTransformer();
                    //endregion
                    default:
                        throw new RuntimeException("Invalid key: " + this);
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Build a new key from a tag.
         *
         * @param tag must be one of {@link Tag}.
         * @return a new key.
         */
        public static Key fromTag(@NonNull @Tag String tag) {
            return new Key().put(NAME_TAG, tag);
        }
    }

    @Retention(SOURCE)
    @StringDef({
            CropSquareTransformer.TAG,
            CropCircleTransformer.TAG,
            CropRoundedTransformer.TAG,
            ColorOverlayTransformer.TAG,
            ColorGrayscaleTransformer.TAG
    })
    private @interface Tag {
    }
}