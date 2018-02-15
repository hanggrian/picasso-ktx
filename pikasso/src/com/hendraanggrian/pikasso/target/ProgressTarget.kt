package com.hendraanggrian.pikasso.target

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.CallSuper
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target

abstract class ProgressTarget : Target {

    private var _onLoaded: ((Bitmap, LoadedFrom) -> Unit)? = null
    private var _onFailed: ((Drawable?) -> Unit)? = null
    private var _onPrepare: ((Drawable?) -> Unit)? = null

    @CallSuper
    override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
        _onLoaded?.invoke(bitmap, from)
    }

    @CallSuper
    override fun onBitmapFailed(errorDrawable: Drawable?) {
        _onFailed?.invoke(errorDrawable)
    }

    @CallSuper
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        _onPrepare?.invoke(placeHolderDrawable)
    }

    fun onLoaded(loaded: (Bitmap, from: LoadedFrom) -> Unit): ProgressTarget {
        _onLoaded = loaded
        return this
    }

    fun onFailed(failed: (Drawable?) -> Unit): ProgressTarget {
        _onFailed = failed
        return this
    }

    fun onPrepare(prepare: (Drawable?) -> Unit): ProgressTarget {
        _onPrepare = prepare
        return this
    }
}