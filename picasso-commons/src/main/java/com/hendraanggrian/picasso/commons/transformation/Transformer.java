package com.hendraanggrian.picasso.commons.transformation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Transformation;

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

    static final String EXTRA_KEY_TITLE = "EXTRA_KEY_TITLE";

    /**
     * Logic of image transformation should happen here.
     */
    @NonNull
    protected abstract Bitmap transform(@NonNull Bitmap source, boolean recycleSource);

    @NonNull
    protected abstract Bundle keyBundle();

    /**
     * Implemented from <tt>Transformation</tt> and should only be invoked by <tt>Picasso</tt>.
     * Calling this method outside <tt>Picasso</tt> will cause the source bitmap to be recycled and
     * likely to cause errors.
     */
    @NonNull
    @Override
    public Bitmap transform(Bitmap source) {
        return transform(source, true);
    }

    @Override
    public String key() {
        Bundle bundle = keyBundle();
        String title = bundle.getString(EXTRA_KEY_TITLE);
        bundle.remove(EXTRA_KEY_TITLE);
        String attributes = "";
        for (String key : bundle.keySet())
            attributes += key + "=" + bundle.getString(key) + ", ";
        if (attributes.endsWith(", "))
            attributes = attributes.substring(0, attributes.length() - 2);
        return title + "[{" + attributes + "}]";
    }

    /**
     * Transform source bitmap to target bitmap.
     */
    @NonNull
    public Bitmap toBitmap(@NonNull Bitmap source) {
        return transform(source, false);
    }

    /**
     * Transform source drawable to target bitmap.
     */
    @NonNull
    public Bitmap toBitmap(@NonNull Drawable source) {
        return toBitmap(((BitmapDrawable) source).getBitmap());
    }

    /**
     * Transform source drawable from resources to target bitmap.
     */
    @NonNull
    public Bitmap toBitmap(@NonNull Context context, @DrawableRes int sourceRes) {
        return toBitmap(ContextCompat.getDrawable(context, sourceRes));
    }

    /**
     * Transform source bitmap to target drawable.
     */
    @NonNull
    public Drawable toDrawable(@NonNull Context context, @NonNull Bitmap source) {
        return new BitmapDrawable(context.getResources(), toBitmap(source));
    }

    /**
     * Transform source drawable to target drawable.
     */
    @NonNull
    public Drawable toDrawable(@NonNull Context context, @NonNull Drawable source) {
        return new BitmapDrawable(context.getResources(), toBitmap(source));
    }

    /**
     * Transform source drawable from resources to target drawable.
     */
    @NonNull
    public Drawable toDrawable(@NonNull Context context, @DrawableRes int sourceRes) {
        return toDrawable(context, ContextCompat.getDrawable(context, sourceRes));
    }

    /**
     * Creates new bitmap with default configuration, only used in subclasses of <tt>Transformer</tt>.
     */
    @NonNull
    protected Bitmap createDefaultBitmap(@NonNull Bitmap source) {
        return Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
    }
}