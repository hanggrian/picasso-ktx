package com.hendraanggrian.picasso.palette

import androidx.palette.graphics.Palette
import com.squareup.picasso.RequestCreator

/** Build and returns the [Palette] synchronously. */
inline val RequestCreator.palette: Palette
    get() = Palette.from(get()).generate()
