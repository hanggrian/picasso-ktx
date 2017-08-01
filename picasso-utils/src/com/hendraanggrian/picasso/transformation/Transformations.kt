package com.hendraanggrian.picasso.transformation

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import com.hendraanggrian.kota.content.toPx
import com.squareup.picasso.Transformation

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
object Transformations {

    //region crop
    fun square(): Transformation = CropSquareTransformer()

    fun circle(): Transformation = CropCircleTransformer()

    @JvmOverloads
    fun rounded(radius: Int, margin: Int, useDp: Boolean = false): Transformation = CropRoundedTransformer(
            if (useDp) radius.toPx() else radius,
            if (useDp) margin.toPx() else margin)
    //endregion

    //region color
    @JvmOverloads
    fun overlay(@ColorInt color: Int, @IntRange(from = 0x0, to = 0xFF) alpha: Int? = null): Transformation {
        if (alpha == null) {
            return ColorOverlayTransformer(color)
        }
        check(alpha in 0..255, { "alpha must be between 0 and 255." })
        return ColorOverlayTransformer(color and 0x00ffffff or (alpha shl 24))
    }

    fun grayscale(): Transformation = ColorGrayscaleTransformer()

    fun mask(context: Context, @DrawableRes maskId: Int): Transformation = MaskTransformer(context, maskId)
    //endregion
}