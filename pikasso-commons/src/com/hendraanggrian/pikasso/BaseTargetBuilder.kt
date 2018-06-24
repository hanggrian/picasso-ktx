package com.hendraanggrian.pikasso

import android.graphics.drawable.Drawable
import java.lang.Exception

interface BaseTargetBuilder {

    /** Invoked when image failed to load. */
    fun onFailed(callback: (e: Exception, Drawable?) -> Unit)

    /** Invoked when image has started loading. */
    fun onPrepare(callback: (Drawable?) -> Unit)
}