package com.hendraanggrian.picasso.palette

import android.widget.ImageView
import androidx.palette.graphics.Palette
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/** Build the [Palette] asynchronously. */
fun RequestCreator.paletteInto(
    asynchronous: Boolean = true,
    builder: PaletteTargetBuilder.() -> Unit
): Target = PaletteTargetBuilderImpl(asynchronous).also {
    it.builder()
    into(it)
}

/** Build the [Palette] asynchronously from loaded target image. */
fun RequestCreator.paletteInto(
    target: ImageView,
    asynchronous: Boolean = true,
    builder: PaletteCallbackBuilder.() -> Unit
): Unit = into(target, PaletteCallbackBuilderImpl(target, asynchronous).apply(builder))
