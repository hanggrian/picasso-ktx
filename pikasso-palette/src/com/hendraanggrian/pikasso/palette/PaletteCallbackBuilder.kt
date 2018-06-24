package com.hendraanggrian.pikasso.palette

import java.lang.Exception

interface PaletteCallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: PaletteBuilder.() -> Unit)

    /** Invoked when image failed to load. */
    fun onError(callback: (e: Exception) -> Unit)
}