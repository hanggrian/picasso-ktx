package com.hendraanggrian.pikasso

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

interface BaseTargetBuilder {

    /** Invoked when image failed to load. */
    fun onFailed(callback: (e: java.lang.Exception, Drawable?) -> Unit)

    /** Invoked when image has started loading. */
    fun onPrepare(callback: (Drawable?) -> Unit)
}

interface TargetBuilder : BaseTargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(callback: (Bitmap, from: Picasso.LoadedFrom) -> Unit)
}

@PublishedApi
@Suppress("ClassName")
internal class _Target : Target, TargetBuilder {
    private var onLoaded: ((Bitmap, Picasso.LoadedFrom) -> Unit)? = null
    private var onFailed: ((Exception, Drawable?) -> Unit)? = null
    private var onPrepare: ((Drawable?) -> Unit)? = null

    override fun onLoaded(callback: (Bitmap, from: Picasso.LoadedFrom) -> Unit) {
        onLoaded = callback
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        onLoaded?.invoke(bitmap, from)
    }

    override fun onFailed(callback: (Exception, Drawable?) -> Unit) {
        onFailed = callback
    }

    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
        onFailed?.invoke(e, errorDrawable)
    }

    override fun onPrepare(callback: (Drawable?) -> Unit) {
        onPrepare = callback
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        onPrepare?.invoke(placeHolderDrawable)
    }
}
