package com.hendraanggrian.pikasso.palette

import android.graphics.Color
import androidx.annotation.ColorInt
import androidx.palette.graphics.Palette

interface PaletteBuilder {

    /** Instance of [Palette] generated asynchronously. */
    val palette: Palette

    /** Use vibrant color. */
    fun useVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getVibrantColor(defaultColor))

    /** Use light useVibrant color. */
    fun useLightVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getLightVibrantColor(defaultColor))

    /** Use dark useVibrant color. */
    fun useDarkVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getDarkVibrantColor(defaultColor))

    /** Use muted color. */
    fun useMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getMutedColor(defaultColor))

    /** Use light muted color. */
    fun useLightMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getLightMutedColor(defaultColor))

    /** Use dark muted color. */
    fun useDarkMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getDarkMutedColor(defaultColor))

    /** Use dominant. */
    fun useDominant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ): Unit = block(palette.getDominantColor(defaultColor))

    companion object {

        internal fun from(palette: Palette): PaletteBuilder = object : PaletteBuilder {
            override val palette: Palette = palette
        }
    }
}