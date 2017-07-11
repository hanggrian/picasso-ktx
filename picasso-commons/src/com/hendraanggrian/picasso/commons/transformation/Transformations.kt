package com.hendraanggrian.picasso.commons.transformation

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import android.support.v4.content.ContextCompat

/**
 * Image transformations with <tt>Picasso</tt>.
 *
 *
 * To use with <tt>Picasso</tt>:
 * <pre>`
 * Picasso.with(context)
 * .load(image)
 * .transform(Transformations.circle())
 * .into(target);
`</pre> *
 *
 *
 * To manipulate image outside <tt>Picasso</tt>:
 * <pre>`
 * Bitmap circledBitmap = Transformations.circle().toBitmap(...);
 * Drawable grayscaleDrawable = Transformations.grayscale().toDrawable(...);
`</pre> *

 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
object Transformations {

    //region crop
    fun square(): Transformer = CropSquareTransformer()

    fun circle(): Transformer = CropCircleTransformer()

    @JvmOverloads
    fun rounded(radius: Int, margin: Int, useDp: Boolean = false): Transformer = CropRoundedTransformer(
            if (useDp) (radius * Resources.getSystem().displayMetrics.density).toInt() else radius,
            if (useDp) (margin * Resources.getSystem().displayMetrics.density).toInt() else margin)
    //endregion

    //region color
    @JvmOverloads
    fun overlay(@ColorInt color: Int, @IntRange(from = 0x0, to = 0xFF) alpha: Int? = null): Transformer {
        if (alpha == null) {
            return ColorOverlayTransformer(color)
        }
        check(alpha in 0..255, { "alpha must be between 0 and 255." })
        return ColorOverlayTransformer(color and 0x00ffffff or (alpha shl 24))
    }

    fun grayscale(): Transformer = ColorGrayscaleTransformer()

    fun mask(mask: Drawable): Transformer = MaskTransformer(mask)

    fun mask(context: Context, @DrawableRes maskId: Int): Transformer = MaskTransformer(ContextCompat.getDrawable(context, maskId) ?: throw IllegalArgumentException("maskId is invalid"))
    //endregion
}