@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

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
): Unit = into(target, _PaletteCallbackBuilder(
    target,
    asynchronous
).apply(builder))