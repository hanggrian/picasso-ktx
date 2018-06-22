package com.hendraanggrian.pikasso.palette

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v7.graphics.Palette

interface PaletteBuilder {

    /** Instance of [Palette] generated asynchronously. */
    val palette: Palette

    /** Use vibrant color. */
    fun vibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getVibrantColor(defaultColor))

    /** Use light vibrant color. */
    fun lightVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getLightVibrantColor(defaultColor))

    /** Use dark vibrant color. */
    fun darkVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getDarkVibrantColor(defaultColor))

    /** Use muted color. */
    fun muted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getMutedColor(defaultColor))

    /** Use light muted color. */
    fun lightMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getLightMutedColor(defaultColor))

    /** Use dark muted color. */
    fun darkMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getDarkMutedColor(defaultColor))

    /** Use dominant. */
    fun dominant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getDominantColor(defaultColor))
}