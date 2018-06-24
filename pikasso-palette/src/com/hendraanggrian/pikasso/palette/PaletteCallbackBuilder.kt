package com.hendraanggrian.pikasso.palette

import com.hendraanggrian.pikasso.BaseCallbackBuilder

interface PaletteCallbackBuilder : BaseCallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: PaletteBuilder.() -> Unit)
}