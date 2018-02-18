@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.annotation.IntRange
import com.hendraanggrian.pikasso.transformation.ColorGrayscaleTransformation
import com.hendraanggrian.pikasso.transformation.ColorOverlayTransformation
import com.hendraanggrian.pikasso.transformation.CropCircleTransformation
import com.hendraanggrian.pikasso.transformation.CropRoundedTransformation
import com.hendraanggrian.pikasso.transformation.CropSquareTransformation
import com.hendraanggrian.pikasso.transformation.MaskTransformation
import com.squareup.picasso.RequestCreator

inline fun RequestCreator.square(): RequestCreator = transform(CropSquareTransformation())

inline fun RequestCreator.circle(): RequestCreator = transform(CropCircleTransformation())

inline fun RequestCreator.rounded(
    radius: Int,
    margin: Int
): RequestCreator = transform(CropRoundedTransformation(radius, margin))

inline fun RequestCreator.overlay(
    @ColorInt color: Int
): RequestCreator = transform(ColorOverlayTransformation(color))

inline fun RequestCreator.overlay(
    @ColorInt color: Int,
    @IntRange(from = 0x0, to = 0xFF) alpha: Int
): RequestCreator = transform(ColorOverlayTransformation(color, alpha))

inline fun RequestCreator.grayscale(): RequestCreator = transform(ColorGrayscaleTransformation())

inline fun RequestCreator.mask(
    context: Context,
    @DrawableRes maskId: Int
): RequestCreator = transform(MaskTransformation(context, maskId))