@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso.transformations

import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import com.hendraanggrian.pikasso.transformations.internal.CropCircleTransformation
import com.hendraanggrian.pikasso.transformations.internal.CropRoundedTransformation
import com.hendraanggrian.pikasso.transformations.internal.CropSquareTransformation
import com.hendraanggrian.pikasso.transformations.internal.GrayscaleTransformation
import com.hendraanggrian.pikasso.transformations.internal.MaskTransformation
import com.hendraanggrian.pikasso.transformations.internal.OverlayTransformation
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