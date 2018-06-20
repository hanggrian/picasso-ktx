package com.hendraanggrian.pikasso.transformations

import android.graphics.Bitmap
import android.graphics.Bitmap.createBitmap
import com.squareup.picasso.Transformation
import kotlin.math.min

class CropSquareTransformation : Transformation {

    private var width = 0
    private var height = 0

    override fun transform(source: Bitmap): Bitmap {
        val size = min(source.width, source.height)
        width = (source.width - size) / 2
        height = (source.height - size) / 2
        val target = createBitmap(source, width, height, size, size)
        if (target != source) source.recycle()
        return target
    }

    override fun key() = "CropSquareTransformation(width=$width, height=$height)"
}