package com.example.pikasso

import android.content.res.Resources.getSystem
import android.util.DisplayMetrics.DENSITY_DEFAULT
import androidx.annotation.Px

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @return A float value to represent px equivalent to dp depending on device density
 */
val Float.px: Float
    @Px get() = this * (getSystem().displayMetrics.densityDpi.toFloat() / DENSITY_DEFAULT)

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @return A float value to represent dp equivalent to px value
 */
val Float.dp: Float
    get() = this / (getSystem().displayMetrics.densityDpi.toFloat() / DENSITY_DEFAULT)

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 *
 * @return A float value to represent px equivalent to dp depending on device density
 */
val Int.px: Int
    @Px get() = this * (getSystem().displayMetrics.densityDpi / DENSITY_DEFAULT)

/**
 * This method converts device specific pixels to density independent pixels.
 *
 * @return A float value to represent dp equivalent to px value
 */
val Int.dp: Int
    get() = this / (getSystem().displayMetrics.densityDpi / DENSITY_DEFAULT)