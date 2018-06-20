@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso.palette

import android.graphics.drawable.BitmapDrawable
import android.support.v7.graphics.Palette
import android.widget.ImageView
import com.hendraanggrian.pikasso.into
import com.hendraanggrian.pikasso.palette.internal._PaletteBuilder
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

inline fun RequestCreator.intoPalette(
    target: ImageView,
    noinline builder: PaletteBuilder.() -> Unit
) = into(target) {
    onSuccess {
        Palette.from((target.drawable as BitmapDrawable).bitmap)
            .generate { _PaletteBuilder(it).apply(builder) }
    }
}

inline fun RequestCreator.intoPalette(
    noinline builder: PaletteBuilder.() -> Unit
): Target = into {
    onLoaded { bitmap, _ ->
        Palette.from(bitmap)
            .generate { _PaletteBuilder(it).apply(builder) }
    }
}