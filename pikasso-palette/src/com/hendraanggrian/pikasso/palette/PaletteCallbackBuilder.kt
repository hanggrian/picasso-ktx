package com.hendraanggrian.pikasso.palette

import android.support.v7.graphics.Palette
import java.lang.Exception

interface PaletteCallbackBuilder {

    fun onSuccess(callback: PaletteBuilder.(Palette) -> Unit)

    fun onError(callback: (e: Exception) -> Unit)
}