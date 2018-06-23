package com.hendraanggrian.pikasso.palette.internal

import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.hendraanggrian.pikasso.palette.PaletteBuilder
import com.hendraanggrian.pikasso.palette.PaletteCallbackBuilder
import com.squareup.picasso.Callback
import java.lang.Exception

@Suppress("ClassName")
class _PaletteCallbackBuilder(private val target: ImageView) : Callback, PaletteCallbackBuilder {
    private var onSuccess: (PaletteBuilder.(Palette) -> Unit)? = null
    private var onError: ((Exception) -> Unit)? = null

    override fun onSuccess(callback: PaletteBuilder.(Palette) -> Unit) {
        onSuccess = callback
    }

    override fun onSuccess() {
        Palette.from((target.drawable as BitmapDrawable).bitmap)
            .generate { palette ->
                if (onSuccess != null) onSuccess!!.invoke(_PaletteBuilder(palette), palette)
            }
    }

    override fun onError(callback: (e: Exception) -> Unit) {
        onError = callback
    }

    override fun onError(e: Exception) {
        onError?.invoke(e)
    }
}