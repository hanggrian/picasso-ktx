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
 * Superclass of all <tt>Transformation</tt>. In addition to transforming image with <tt>Picasso</tt>,
 * <tt>Transformer</tt> also transforms <tt>Bitmap</tt> to <tt>Drawable</tt> and vice versa.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public abstract class Transformer implements Transformation {

    /**
     * Creates new <tt>Bitmap</tt> with default configuration. Only used in subclasses of <tt>Transformer</tt>.
     *
     * @param source <tt>Bitmap</tt>.
     * @return empty <tt>Bitmap</tt>.
     */
    @NonNull
    protected Bitmap createDefaultBitmap(@NonNull Bitmap source) {
        return Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }

    /**
     * Transform <tt>Drawable</tt> to <tt>Bitmap</tt>.
     *
     * @param source <tt>Drawable</tt>, should not be null.
     * @return <tt>Bitmap</tt>, never null.
     */
    @NonNull
    public Bitmap transform(@NonNull Drawable source) {
        return transform(((BitmapDrawable) source).getBitmap());
    }

    /**
     * Transform <tt>Drawable</tt> from resources to <tt>Bitmap</tt>.
     *
     * @param context   <tt>Context</tt>, should not be null.
     * @param sourceRes resource id of <tt>Drawable</tt>.
     * @return <tt>Bitmap</tt>, never null.
     */
    @NonNull
    public Bitmap transform(@NonNull Context context, @DrawableRes int sourceRes) {
        return transform(ContextCompat.getDrawable(context, sourceRes));
    }

    /**
     * Transform <tt>Bitmap</tt> to <tt>Drawable</tt>.
     *
     * @param context <tt>Context</tt>, should not be null.
     * @param source  <tt>Bitmap</tt>, should not be null.
     * @return <tt>Drawable</tt>, never null.
     */
    @NonNull
    public Drawable transformDrawable(@NonNull Context context, @NonNull Bitmap source) {
        return new BitmapDrawable(context.getResources(), transform(source));
    }

    /**
     * Transform <tt>Drawable</tt> to <tt>Drawable</tt>.
     *
     * @param context <tt>Context</tt>, should not be null.
     * @param source  <tt>Drawable</tt>, should not be null.
     * @return <tt>Drawable</tt>, never null.
     */
    @NonNull
    public Drawable transformDrawable(@NonNull Context context, @NonNull Drawable source) {
        return new BitmapDrawable(context.getResources(), transform(source));
    }

    /**
     * Transform <tt>Drawable</tt> from resources to <tt>Drawable</tt>.
     *
     * @param context   <tt>Context</tt>, should not be null.
     * @param sourceRes resource id of <tt>Drawable</tt>.
     * @return <tt>Drawable</tt>, never null.
     */
    @NonNull
    public Drawable transformDrawable(@NonNull Context context, @DrawableRes int sourceRes) {
        return transformDrawable(context, ContextCompat.getDrawable(context, sourceRes));
    }

    /**
     * <tt>JSONObject</tt> with builder pattern that will ultimately be converted to String
     * that will be passed to {@link Transformation#key()}.
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
         * @return this <tt>Key</tt>, never null.
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
         * @return this <tt>Key</tt>, never null.
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
         * Creates a new <tt>Transformation</tt> based on this <tt>Key</tt>'s properties.
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

        @Retention(SOURCE)
        @StringDef({
                CropSquareTransformer.TAG,
                CropCircleTransformer.TAG,
                CropRoundedTransformer.TAG,
                ColorOverlayTransformer.TAG,
                ColorGrayscaleTransformer.TAG
        })
        @interface Tag {
        }

        /**
         * Build a new key from a tag.
         *
         * @param tag must be one of {@link Tag}.
         * @return a new <tt>Key</tt>.
         */
        public static Key fromTag(@NonNull @Tag String tag) {
            return new Key().put(NAME_TAG, tag);
        }
    }
}