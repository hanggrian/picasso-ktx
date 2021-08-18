package com.hendraanggrian.picasso.palette

import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.hendraanggrian.picasso.BaseCallbackBuilder
import com.squareup.picasso.Callback

/**
 * Interface to build [Palette] callback with Kotlin DSL.
 *
 * @see paletteInto
 */
interface PaletteCallbackBuilder : BaseCallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(onSuccess: PaletteBuilder.() -> Unit)
}

internal class PaletteCallbackBuilderImpl(
    private val target: ImageView,
    private val asynchronous: Boolean
) : Callback, PaletteCallbackBuilder {
    private var _onSuccess: (PaletteBuilder.() -> Unit)? = null
    private var _onError: ((Exception) -> Unit)? = null

    override fun onSuccess(onSuccess: PaletteBuilder.() -> Unit) {
        _onSuccess = onSuccess
    }

    override fun onSuccess() {
        if (_onSuccess != null) {
            val builder = Palette.from((target.drawable as BitmapDrawable).bitmap)
            when {
                !asynchronous -> _onSuccess!!(PaletteBuilder.from(builder.generate()))
                else -> builder.generate { palette ->
                    when (palette) {
                        null -> onError(PaletteException())
                        else -> _onSuccess!!(PaletteBuilder.from(palette))
                    }
                }
            }
            when {
                asynchronous -> builder.generate {
                    _onSuccess!!.invoke(PaletteBuilder.from(it!!))
                }
                else -> _onSuccess!!(PaletteBuilder.from(builder.generate()))
            }
        }
    }

    override fun onError(onError: (e: Exception) -> Unit) {
        _onError = onError
    }

    override fun onError(e: Exception) {
        _onError?.invoke(e)
    }
}
