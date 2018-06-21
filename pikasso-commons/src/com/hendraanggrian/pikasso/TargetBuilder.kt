package com.hendraanggrian.pikasso

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import java.lang.Exception

interface TargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(callback: (Bitmap, from: Picasso.LoadedFrom) -> Unit)

    /** Invoked when image failed to load. */
    fun onFailed(callback: (e: Exception, Drawable?) -> Unit)

    /** Invoked when image has started loading. */
    fun onPrepare(callback: (Drawable?) -> Unit)
}