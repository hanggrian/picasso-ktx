@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso.palette

import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.hendraanggrian.pikasso.palette.internal._PaletteCallbackBuilder
import com.hendraanggrian.pikasso.palette.internal._PaletteTargetBuilder
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/** Build and returns the [Palette] synchronously. */
inline val RequestCreator.palette: Palette get() = Palette.from(get()).generate()

/** Build the [Palette] asynchronously. */
inline fun RequestCreator.palette(
    asynchronous: Boolean = true,
    noinline builder: PaletteTargetBuilder.() -> Unit
): Target = _PaletteTargetBuilder(asynchronous).also {
    it.builder()
    into(it)
}

/** Build the [Palette] asynchronously from loaded target image. */
inline fun RequestCreator.palette(
    target: ImageView,
    asynchronous: Boolean = true,
    noinline builder: PaletteCallbackBuilder.() -> Unit
): Unit = into(target, _PaletteCallbackBuilder(target, asynchronous).apply(builder))