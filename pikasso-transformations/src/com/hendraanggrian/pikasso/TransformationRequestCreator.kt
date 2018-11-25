@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.hendraanggrian.pikasso.transformations.CropCircleTransformation
import com.hendraanggrian.pikasso.transformations.CropRoundedTransformation
import com.hendraanggrian.pikasso.transformations.CropSquareTransformation
import com.hendraanggrian.pikasso.transformations.GrayscaleTransformation
import com.hendraanggrian.pikasso.transformations.MaskTransformation
import com.hendraanggrian.pikasso.transformations.OverlayTransformation
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
    transform(OverlayTransformation(color))

/** Transform image to grayscale color. */
inline fun RequestCreator.grayscale(): RequestCreator =
    transform(GrayscaleTransformation())

/** Transform image to masked. */
inline fun RequestCreator.mask(drawable: Drawable): RequestCreator =
    transform(MaskTransformation(drawable))