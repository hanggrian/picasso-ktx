package com.hendraanggrian.pikasso.transformations

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Paint
import com.squareup.picasso.Transformation

@PublishedApi
internal class GrayscaleTransformation : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val target = createBitmap(source.width, source.height, ARGB_8888)
        Canvas(target).drawBitmap(source, 0f, 0f, Paint().apply {
            colorFilter = ColorMatrixColorFilter(ColorMatrix().apply { setSaturation(0f) })
        })
        source.recycle()
        return target
    }

    override fun key(): String = "GrayscaleTransformation()"
}
