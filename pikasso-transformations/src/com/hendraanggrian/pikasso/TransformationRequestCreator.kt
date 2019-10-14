package com.hendraanggrian.pikasso

import android.graphics.drawable.Drawable
import androidx.annotation.ColorInt
import com.hendraanggrian.pikasso.internal.CropCircleTransformation
import com.hendraanggrian.pikasso.internal.CropRoundedTransformation
import com.hendraanggrian.pikasso.internal.CropSquareTransformation
import com.hendraanggrian.pikasso.internal.GrayscaleTransformation
import com.hendraanggrian.pikasso.internal.MaskTransformation
import com.hendraanggrian.pikasso.internal.OverlayTransformation
import com.squareup.picasso.RequestCreator

/** Transform image to square. */
fun RequestCreator.square(): RequestCreator =
    transform(CropSquareTransformation())

/** Transform image to circle. */
fun RequestCreator.circle(): RequestCreator =
    transform(CropCircleTransformation())

/** Transform image to rounded edge with defined radius and margin. */
fun RequestCreator.rounded(radius: Float, margin: Float): RequestCreator =
    transform(CropRoundedTransformation(radius, margin))

/** Transform image to rounded edge with defined radius and margin. */
@Suppress("NOTHING_TO_INLINE")
inline fun RequestCreator.rounded(radius: Int, margin: Int): RequestCreator =
    rounded(radius.toFloat(), margin.toFloat())

/** Transform image to overlay color, transparency may be applied. */
fun RequestCreator.overlay(@ColorInt color: Int): RequestCreator =
    transform(OverlayTransformation(color))

/** Transform image to grayscale color. */
fun RequestCreator.grayscale(): RequestCreator =
    transform(GrayscaleTransformation())

/** Transform image to masked. */
fun RequestCreator.mask(drawable: Drawable): RequestCreator =
    transform(MaskTransformation(drawable))
