package com.hendraanggrian.picasso.transformation

import android.graphics.*
import com.squareup.picasso.Transformation

internal class ColorGrayscaleTransformation : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val target = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        Canvas(target).drawBitmap(source, 0f, 0f, Paint().apply {
            colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
        })
        source.recycle()
        return target
    }

    override fun key() = "ColorGrayscaleTransformation"
}