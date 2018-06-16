package com.hendraanggrian.pikasso

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.Gravity.BOTTOM
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.FrameLayout.LayoutParams
import android.widget.ImageView
import android.widget.ProgressBar
import com.hendraanggrian.pikasso.internal._Target
import com.hendraanggrian.pikasso.target.PlaceholderTarget
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target
import java.lang.Exception

/** Interface to create [Target] with Kotlin DSL. */
interface TargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(callback: (Bitmap, from: Picasso.LoadedFrom) -> Unit)

    /** Invoked when image failed to load. */
    fun onFailed(callback: (e: Exception, Drawable?) -> Unit)

    /** Invoked when image has started loading. */
    fun onPrepare(callback: (Drawable?) -> Unit)
}

/** Sets circular progress bar with defined width and height. */
fun ImageView.toProgressTarget(
    size: Int = WRAP_CONTENT
): Target {
    val progressBar = ProgressBar(context)
    progressBar.layoutParams = LayoutParams(size, size).apply { gravity = Gravity.CENTER }
    return PlaceholderTarget(this, progressBar)
}

/** Sets horizontal progress with defined gravity. */
fun ImageView.toHorizontalProgressTarget(
    gravity: Int = BOTTOM
): Target {
    val progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
    progressBar.layoutParams = LayoutParams(MATCH_PARENT, WRAP_CONTENT).apply {
        this.gravity = gravity
    }
    progressBar.isIndeterminate = true
    return PlaceholderTarget(this, progressBar)
}

/** Set custom view as target's placeholder. */
@Suppress("NOTHING_TO_INLINE")
inline fun ImageView.toTarget(
    placeholder: View
): Target = PlaceholderTarget(this, placeholder)

/**
 * Completes the request into a [Target] with Kotlin DSL, returning the [Target] created.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    builder: TargetBuilder.() -> Unit
): Target {
    val target = _Target().apply(builder)
    into(target)
    return target
}