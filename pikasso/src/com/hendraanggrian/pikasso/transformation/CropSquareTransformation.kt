package com.hendraanggrian.pikasso.transformation

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import com.squareup.picasso.Transformation

internal class CropSquareTransformation : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)
        val width = (source.width - size) / 2
        val height = (source.height - size) / 2
        val target = createBitmap(source, width, height, size, size)
        if (target != source) {
            source.recycle()
        }
        return target
    }

    override fun key() = "CropSquareTransformation"
}