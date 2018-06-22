@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso.palette

import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.hendraanggrian.pikasso.into
import com.hendraanggrian.pikasso.palette.internal._PaletteBuilder
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/** Build and returns the [Palette] synchronously. */
inline val RequestCreator.palette: Palette get() = Palette.from(get()).generate()

/** Build the [Palette] asynchronously. */
inline fun RequestCreator.palette(
    noinline builder: PaletteBuilder.() -> Unit
): Target = into {
    onLoaded { bitmap, _ ->
        Palette.from(bitmap)
            .generate { _PaletteBuilder(it).apply(builder) }
    }
}

/** Build the [Palette] asynchronously from loaded target image. */
inline fun RequestCreator.palette(
    target: ImageView,
    noinline builder: PaletteBuilder.() -> Unit
): Unit = into(target) {
    onSuccess {
        Palette.from((target.drawable as BitmapDrawable).bitmap)
            .generate { _PaletteBuilder(it).apply(builder) }
    }
}