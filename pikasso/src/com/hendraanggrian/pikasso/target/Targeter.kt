package com.hendraanggrian.pikasso.target

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.annotation.CallSuper

import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

abstract class Targeter : Target {

    private var mCallback: Target? = null

    @CallSuper
    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        mCallback?.onBitmapLoaded(bitmap, from)
    }

    @CallSuper
    override fun onBitmapFailed(errorDrawable: Drawable?) {
        mCallback?.onBitmapFailed(errorDrawable)
    }

    @CallSuper
    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        mCallback?.onPrepareLoad(placeHolderDrawable)
    }

    fun callback(callback: Target?): Targeter {
        mCallback = callback
        return this
    }

    @JvmOverloads
    fun callback(
            onLoaded: ((bitmap: Bitmap, from: Picasso.LoadedFrom) -> Unit)?,
            onFailed: ((errorDrawable: Drawable?) -> Unit)? = null,
            onPrepare: ((placeholderDrawable: Drawable?) -> Unit)? = null
    ): Targeter = callback(object : Target {
        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            onLoaded?.invoke(bitmap, from)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
            onFailed?.invoke(errorDrawable)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            onPrepare?.invoke(placeHolderDrawable)
        }
    })
}