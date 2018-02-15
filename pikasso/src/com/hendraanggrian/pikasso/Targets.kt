@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.view.Gravity
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import android.widget.ProgressBar
import com.hendraanggrian.pikasso.target.PlaceholderProgress
import com.hendraanggrian.pikasso.target.ProgressTarget

/** Sets circular progress bar with defined width and height. */
fun ImageView.toProgressBarTarget(size: Int = WRAP_CONTENT): ProgressTarget {
    val progressBar = ProgressBar(context)
    progressBar.layoutParams = LayoutParams(size, size).apply { gravity = Gravity.CENTER }
    return PlaceholderProgress(this, progressBar)
}

/** Sets horizontal progress with defined gravity. */
fun ImageView.toHorizontalProgressTarget(gravity: Int = Gravity.BOTTOM): ProgressTarget {
    val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
    progressBar.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply { this.gravity = gravity }
    progressBar.isIndeterminate = true
    return PlaceholderProgress(this, progressBar)
}

/** Set custom view as target's placeholder. */
inline fun ImageView.toProgressTarget(placeholder: View): ProgressTarget = PlaceholderProgress(this, placeholder)