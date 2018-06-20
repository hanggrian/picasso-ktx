package com.hendraanggrian.pikasso.palette

import android.graphics.Color
import android.support.annotation.ColorInt
import android.support.v7.graphics.Palette

interface PaletteBuilder {

    val palette: Palette

    fun vibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getVibrantColor(defaultColor))

    fun lightVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getLightVibrantColor(defaultColor))

    fun darkVibrant(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getDarkVibrantColor(defaultColor))

    fun muted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getMutedColor(defaultColor))

    fun lightMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getLightMutedColor(defaultColor))

    fun darkMuted(
        @ColorInt defaultColor: Int = Color.TRANSPARENT,
        block: (Int) -> Unit
    ) = block(palette.getDarkMutedColor(defaultColor))
}