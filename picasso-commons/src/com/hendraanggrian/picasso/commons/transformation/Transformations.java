package com.hendraanggrian.picasso.commons.transformation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

/**
 * Image transformations with <tt>Picasso</tt>.
 * <p>
 * To use with <tt>Picasso</tt>:
 * <pre><code>
 * Picasso.with(context)
 *      .load(image)
 *      .transform(Transformations.circle())
 *      .into(target);
 * </code></pre>
 * <p>
 * To manipulate image outside <tt>Picasso</tt>:
 * <pre><code>
 * Bitmap circledBitmap = Transformations.circle().toBitmap(...);
 * Drawable grayscaleDrawable = Transformations.grayscale().toDrawable(...);
 * </code></pre>
 *
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
public final class Transformations {

    private Transformations() {
    }

    //region crop
    @NonNull
    public static Transformer square() {
        return new CropSquareTransformer();
    }

    @NonNull
    public static Transformer circle() {
        return new CropCircleTransformer();
    }

    @NonNull
    public static Transformer rounded(int radius, int margin) {
        return rounded(radius, margin, false);
    }

    @NonNull
    public static Transformer rounded(int radius, int margin, boolean useDp) {
        return new CropRoundedTransformer(
                useDp ? (int) (radius * Resources.getSystem().getDisplayMetrics().density) : radius,
                useDp ? (int) (margin * Resources.getSystem().getDisplayMetrics().density) : margin
        );
    }
    //endregion

    //region color
    @NonNull
    public static Transformer overlay(@NonNull Context context, @ColorRes int colorRes) {
        return overlay(ContextCompat.getColor(context, colorRes));
    }

    @NonNull
    public static Transformer overlay(@NonNull Context context, @ColorRes int colorRes, int alpha) {
        return overlay(ContextCompat.getColor(context, colorRes), alpha);
    }

    @NonNull
    public static Transformer overlay(@ColorInt int color) {
        return new ColorOverlayTransformer(color);
    }

    @NonNull
    public static Transformer overlay(@ColorInt int color, @IntRange(from = 0x0, to = 0xFF) int alpha) {
        if (alpha < 0 || alpha > 255) {
            throw new IllegalArgumentException("alpha must be between 0 and 255.");
        }
        color = (color & 0x00ffffff) | (alpha << 24);
        return new ColorOverlayTransformer(color);
    }

    @NonNull
    public static Transformer grayscale() {
        return new ColorGrayscaleTransformer();
    }

    @NonNull
    public static Transformer mask(@NonNull Drawable mask) {
        return new MaskTransformer(mask);
    }

    @NonNull
    public static Transformer mask(@NonNull Context context, @DrawableRes int maskId) {
        Drawable mask = ContextCompat.getDrawable(context, maskId);
        if (mask == null) {
            throw new IllegalArgumentException("maskId is invalid");
        }
        return new MaskTransformer(mask);
    }
    //endregion
}