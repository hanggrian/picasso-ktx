package com.hendraanggrian.pikasso.demo

import android.content.res.Resources.getSystem
import android.util.DisplayMetrics.DENSITY_DEFAULT
import androidx.annotation.Px

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
val Float.px: Float
    @Px get() = this * (getSystem().displayMetrics.densityDpi.toFloat() / DENSITY_DEFAULT)

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
val Float.dp: Float
    get() = this / (getSystem().displayMetrics.densityDpi.toFloat() / DENSITY_DEFAULT)

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @param dp A value in dp (density independent pixels) unit. Which we need to convert into pixels
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent px equivalent to dp depending on device density
 */
val Int.px: Int
    @Px get() = this * (getSystem().displayMetrics.densityDpi / DENSITY_DEFAULT)

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @param px A value in px (pixels) unit. Which we need to convert into db
 * @param context Context to get resources and device specific display metrics
 * @return A float value to represent dp equivalent to px value
 */
val Int.dp: Int
    get() = this / (getSystem().displayMetrics.densityDpi / DENSITY_DEFAULT)