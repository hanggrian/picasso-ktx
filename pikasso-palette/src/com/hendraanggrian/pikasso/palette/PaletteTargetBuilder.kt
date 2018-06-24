package com.hendraanggrian.pikasso.palette

import android.graphics.Bitmap
import com.hendraanggrian.pikasso.BaseTargetBuilder
import com.squareup.picasso.Picasso

interface PaletteTargetBuilder : BaseTargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(callback: PaletteBuilder.(Bitmap, from: Picasso.LoadedFrom) -> Unit)
}