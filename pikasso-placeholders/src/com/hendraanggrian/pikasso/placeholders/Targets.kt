package com.hendraanggrian.pikasso.placeholders

import android.view.Gravity.BOTTOM
import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Target

/** Set custom view as target's placeholder. */
@Suppress("NOTHING_TO_INLINE")
inline fun ImageView.toTarget(
    placeholder: View
): Target = PlaceholderTarget(this, placeholder)

/** Sets circular progress bar with defined width and height. */
fun ImageView.toProgressTarget(
    size: Int = WRAP_CONTENT
): Target {
    val progressBar = ProgressBar(context)
    progressBar.layoutParams = FrameLayout.LayoutParams(size, size).apply { gravity = CENTER }
    return toTarget(progressBar)
}

/** Sets horizontal progress with defined gravity. */
fun ImageView.toHorizontalProgressTarget(
    gravity: Int = BOTTOM
): Target {
    val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
    progressBar.layoutParams = FrameLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
        this.gravity = gravity
    }
    progressBar.isIndeterminate = true
    return toTarget(progressBar)
}