package com.hendraanggrian.pikasso.palette.internal

import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.hendraanggrian.pikasso.palette.PaletteBuilder
import com.hendraanggrian.pikasso.palette.PaletteCallbackBuilder
import com.squareup.picasso.Callback
import java.lang.Exception

@PublishedApi
@Suppress("ClassName")
internal class _PaletteCallbackBuilder(
    private val target: ImageView,
    private val asynchronous: Boolean
) : Callback, PaletteCallbackBuilder {
    private var onSuccess: (PaletteBuilder.() -> Unit)? = null
    private var onError: ((Exception) -> Unit)? = null

    override fun onSuccess(callback: PaletteBuilder.() -> Unit) {
        onSuccess = callback
    }

    override fun onSuccess() {
        if (onSuccess != null) {
            val builder = Palette.from((target.drawable as BitmapDrawable).bitmap)
            when {
                asynchronous -> builder.generate { onSuccess!!.invoke(PaletteBuilder.from(it)) }
                else -> onSuccess!!.invoke(PaletteBuilder.from(builder.generate()))
            }
        }
    }

    override fun onError(callback: (e: Exception) -> Unit) {
        onError = callback
    }

    override fun onError(e: Exception) {
        onError?.invoke(e)
    }
}