package com.hendraanggrian.picasso

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import com.hendraanggrian.picasso.transformation.*
import com.squareup.picasso.Transformation

object Transformations {

    //region crop
    fun square(): Transformation = CropSquareTransformation()

    fun circle(): Transformation = CropCircleTransformation()

    fun rounded(radius: Int, margin: Int): Transformation = CropRoundedTransformation(radius, margin)
    //endregion

    //region color
    @JvmOverloads
    fun overlay(@ColorInt color: Int, @IntRange(from = 0x0, to = 0xFF) alpha: Int? = null): Transformation {
        if (alpha == null) return ColorOverlayTransformation(color)
        check(alpha in 0..255, { "alpha must be between 0 and 255." })
        return ColorOverlayTransformation(color and 0x00ffffff or (alpha shl 24))
    }

    fun grayscale(): Transformation = ColorGrayscaleTransformation()
    //endregion

    fun mask(context: Context, @DrawableRes maskId: Int): Transformation = MaskTransformation(context, maskId)
}