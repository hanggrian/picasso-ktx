package com.hendraanggrian.pikasso

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.palette.graphics.Palette
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/**
 * Interface to build [Palette] target with Kotlin DSL.
 *
 * @see palette
 */
interface PaletteTargetBuilder : BaseTargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(onLoaded: PaletteBuilder.(Bitmap, from: Picasso.LoadedFrom) -> Unit)
}

internal class PaletteTargetBuilderImpl(private val asynchronous: Boolean) : Target,
    PaletteTargetBuilder {
    private var _onLoaded: (PaletteBuilder.(Bitmap, Picasso.LoadedFrom) -> Unit)? = null
    private var _onFailed: ((Exception, Drawable?) -> Unit)? = null
    private var _onPrepare: ((Drawable?) -> Unit)? = null

    override fun onLoaded(onLoaded: PaletteBuilder.(Bitmap, from: Picasso.LoadedFrom) -> Unit) {
        _onLoaded = onLoaded
    }

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        if (_onLoaded != null) {
            val builder = Palette.from(bitmap)
            when {
                !asynchronous -> _onLoaded!!(
                    PaletteBuilder.from(
                        builder.generate()
                    ), bitmap, from
                )
                else -> builder.generate { palette ->
                    when (palette) {
                        null -> onBitmapFailed(PaletteException(), null)
                        else -> _onLoaded!!(PaletteBuilder.from(palette), bitmap, from)
                    }
                }
            }
        }
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
