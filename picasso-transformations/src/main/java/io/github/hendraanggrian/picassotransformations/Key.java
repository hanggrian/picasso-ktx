package io.github.hendraanggrian.picassotransformations;

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */

import android.support.annotation.NonNull;
import android.support.annotation.StringDef;

import com.squareup.picasso.Transformation;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.Retention;

import io.github.hendraanggrian.picassotransformations.color.ColorGrayscaleTransformation;
import io.github.hendraanggrian.picassotransformations.color.ColorOverlayTransformation;
import io.github.hendraanggrian.picassotransformations.crop.CropCircleTransformation;
import io.github.hendraanggrian.picassotransformations.crop.CropRoundedTransformation;
import io.github.hendraanggrian.picassotransformations.crop.CropSquareTransformation;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * <tt>JSONObject</tt> with builder pattern that will ultimately be converted to String
 * that will be passed to {@link Transformation#key()}.
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Key extends JSONObject {

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
     * @param value a String.
     * @return this <tt>Key</tt>, never null.
     */
    @NonNull
    public Key put(@NonNull String name, String value) {
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
    Transformation toTransformation() {
        try {
            switch (getString(NAME_TAG)) {
                //region crop
                case CropSquareTransformation.TAG:
                    return new CropSquareTransformation();
                case CropCircleTransformation.TAG:
                    return new CropCircleTransformation();
                case CropRoundedTransformation.TAG:
                    return new CropRoundedTransformation(
                            getInt(CropRoundedTransformation.NAME_RADIUS),
                            getInt(CropRoundedTransformation.NAME_MARGIN));
                //endregion
                //region color
                case ColorOverlayTransformation.TAG:
                    return new ColorOverlayTransformation(
                            getInt(ColorOverlayTransformation.NAME_COLOR));
                case ColorGrayscaleTransformation.TAG:
                    return new ColorGrayscaleTransformation();
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
            CropSquareTransformation.TAG,
            CropCircleTransformation.TAG,
            CropRoundedTransformation.TAG,
            ColorOverlayTransformation.TAG,
            ColorGrayscaleTransformation.TAG
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