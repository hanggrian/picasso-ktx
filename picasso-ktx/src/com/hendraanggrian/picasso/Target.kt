package com.hendraanggrian.picasso

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/** Partial target builder, extended in `pikasso-palette` as well. */
interface BaseTargetBuilder {

    /** Invoked when image failed to load. */
    fun onFailed(onFailed: (e: Exception, Drawable?) -> Unit)

    /** Invoked when image has started loading. */
    fun onPrepare(onPrepare: (Drawable?) -> Unit)
}

/**
 * Interface to build [Target] with Kotlin DSL.
 *
 * @see into
 */
interface TargetBuilder : BaseTargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(onLoaded: (Bitmap, from: Picasso.LoadedFrom) -> Unit)
}

internal class TargetImpl : Target, TargetBuilder {
    private var _onLoaded: ((Bitmap, Picasso.LoadedFrom) -> Unit)? = null
    private var _onFailed: ((Exception, Drawable?) -> Unit)? = null
    private var _onPrepare: ((Drawable?) -> Unit)? = null

    override fun onLoaded(onLoaded: (Bitmap, from: Picasso.LoadedFrom) -> Unit) {
        _onLoaded = onLoaded
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        _onLoaded?.invoke(bitmap, from)
    }

    override fun onFailed(onFailed: (Exception, Drawable?) -> Unit) {
        _onFailed = onFailed
    }

    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
        _onFailed?.invoke(e, errorDrawable)
    }

    override fun onPrepare(onPrepare: (Drawable?) -> Unit) {
        _onPrepare = onPrepare
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        _onPrepare?.invoke(placeHolderDrawable)
    }
}
