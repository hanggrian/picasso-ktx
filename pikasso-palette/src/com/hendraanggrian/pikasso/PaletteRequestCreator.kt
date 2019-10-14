package com.hendraanggrian.pikasso

import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/** Build the [Palette] asynchronously. */
fun RequestCreator.palette(
    asynchronous: Boolean = true,
    builder: PaletteTargetBuilder.() -> Unit
): Target = PaletteTargetBuilderImpl(asynchronous).also {
    it.builder()
    into(it)
}

/** Build the [Palette] asynchronously from loaded target image. */
fun RequestCreator.palette(
    target: ImageView,
    asynchronous: Boolean = true,
    builder: PaletteCallbackBuilder.() -> Unit
): Unit = into(target, PaletteCallbackBuilderImpl(target, asynchronous).apply(builder))
