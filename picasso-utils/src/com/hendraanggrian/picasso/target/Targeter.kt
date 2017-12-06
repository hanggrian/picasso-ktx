package com.hendraanggrian.picasso.target

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.CallSuper

import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

abstract class Targeter : Target {

    private var callback: Target? = null

    @CallSuper
    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        callback?.onBitmapLoaded(bitmap, from)
    }

    @CallSuper
    override fun onBitmapFailed(errorDrawable: Drawable?) {
        callback?.onBitmapFailed(errorDrawable)
    }

    @CallSuper
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        callback?.onPrepareLoad(placeHolderDrawable)
    }

    fun callback(callback: Target?): Targeter {
        this.callback = callback
        return this
    }

    @JvmOverloads
    fun callback(
            loaded: ((bitmap: Bitmap, from: Picasso.LoadedFrom) -> Unit)?,
            failed: ((errorDrawable: Drawable?) -> Unit)? = null,
            prepare: ((placeholderDrawable: Drawable?) -> Unit)? = null
    ): Targeter = callback(object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            loaded?.invoke(bitmap, from)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            failed?.invoke(errorDrawable)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            prepare?.invoke(placeHolderDrawable)
        }
    })
}