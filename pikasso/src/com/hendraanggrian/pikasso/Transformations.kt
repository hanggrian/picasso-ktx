@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import com.hendraanggrian.pikasso.transformation.ColorGrayscaleTransformation
import com.hendraanggrian.pikasso.transformation.ColorOverlayTransformation
import com.hendraanggrian.pikasso.transformation.CropCircleTransformation
import com.hendraanggrian.pikasso.transformation.CropRoundedTransformation
import com.hendraanggrian.pikasso.transformation.CropSquareTransformation
import com.hendraanggrian.pikasso.transformation.MaskTransformation
import com.squareup.picasso.RequestCreator

/** Transform image to square. */
inline fun RequestCreator.square(): RequestCreator =
    transform(CropSquareTransformation())

/** Transform image to circle. */
inline fun RequestCreator.circle(): RequestCreator =
    transform(CropCircleTransformation())

/** Transform image to rounded edge with defined radius and margin. */
inline fun RequestCreator.rounded(radius: Float, margin: Float): RequestCreator =
    transform(CropRoundedTransformation(radius, margin))

/** Transform image to rounded edge with defined radius and margin. */
inline fun RequestCreator.rounded(radius: Int, margin: Int): RequestCreator =
    transform(CropRoundedTransformation(radius, margin))

/** Transform image to overlay color, transparency may be applied. */
inline fun RequestCreator.overlay(@ColorInt color: Int): RequestCreator =
    transform(ColorOverlayTransformation(color))

/** Transform image to grayscale color. */
inline fun RequestCreator.grayscale(): RequestCreator =
    transform(ColorGrayscaleTransformation())

/** Transform image to masked. */
inline fun RequestCreator.mask(drawable: Drawable): RequestCreator =
    transform(MaskTransformation(drawable))