package com.hendraanggrian.pikasso

import android.graphics.Bitmap
import com.squareup.picasso.Picasso

interface TargetBuilder : BaseTargetBuilder {

    /** Invoked when image is successfully loaded. */
    fun onLoaded(callback: (Bitmap, from: Picasso.LoadedFrom) -> Unit)
}