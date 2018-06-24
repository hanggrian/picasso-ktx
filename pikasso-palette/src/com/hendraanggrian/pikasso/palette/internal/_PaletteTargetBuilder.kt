package com.hendraanggrian.pikasso.palette.internal

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.graphics.Palette
import com.hendraanggrian.pikasso.palette.PaletteBuilder
import com.hendraanggrian.pikasso.palette.PaletteTargetBuilder
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.lang.Exception

@PublishedApi
@Suppress("ClassName")
internal class _PaletteTargetBuilder(
    private val asynchronous: Boolean
) : Target, PaletteTargetBuilder {
    private var onLoaded: (PaletteBuilder.(Bitmap, Picasso.LoadedFrom) -> Unit)? = null
    private var onFailed: ((Exception, Drawable?) -> Unit)? = null
    private var onPrepare: ((Drawable?) -> Unit)? = null

    override fun onLoaded(callback: PaletteBuilder.(Bitmap, from: Picasso.LoadedFrom) -> Unit) {
        onLoaded = callback
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        if (onLoaded != null) {
            val builder = Palette.from(bitmap)
            when {
                asynchronous -> builder.generate {
                    onLoaded!!.invoke(PaletteBuilder.from(it), bitmap, from)
                }
                else -> onLoaded!!.invoke(PaletteBuilder.from(builder.generate()), bitmap, from)
            }
        }
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