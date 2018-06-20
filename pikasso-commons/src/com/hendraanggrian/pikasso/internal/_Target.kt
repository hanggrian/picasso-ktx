package com.hendraanggrian.pikasso.internal

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.hendraanggrian.pikasso.TargetBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

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