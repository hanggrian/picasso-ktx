package com.hendraanggrian.pikasso

import android.view.Gravity.CENTER
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ProgressBar
import com.hendraanggrian.pikasso.target.PlaceholderTargeter
import com.hendraanggrian.pikasso.target.Targeter

object Targets {

    /** Set progress bar with defined width and height as target's placeholder. */
    @JvmOverloads
    fun progress(target: ImageView, progressBarSize: Int = WRAP_CONTENT): Targeter = PlaceholderTargeter(target, ProgressBar(target.context).apply {
        layoutParams = FrameLayout.LayoutParams(progressBarSize, progressBarSize).apply { gravity = CENTER }
    })

    /** Set custom view as target's placeholder. */
    fun custom(target: ImageView, placeholder: View): Targeter = PlaceholderTargeter(target, placeholder)
}