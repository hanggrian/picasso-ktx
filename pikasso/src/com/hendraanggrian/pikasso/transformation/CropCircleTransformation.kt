package com.hendraanggrian.pikasso.transformation

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Shader.TileMode.CLAMP
import com.squareup.picasso.Transformation
import kotlin.math.min

class CropCircleTransformation : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val size = min(source.width, source.height)
        val width = (source.width - size) / 2
        val height = (source.height - size) / 2
        val target = createBitmap(size, size, ARGB_8888)
        val r = size / 2f
        Canvas(target).drawCircle(r, r, r, Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(source, CLAMP, CLAMP)
            // source isn't square, move viewport to center
            if (width != 0 || height != 0) shader.setLocalMatrix(Matrix().apply {
                setTranslate(-width.toFloat(), -height.toFloat())
            })
        })
        source.recycle()
        return target
    }

    override fun key() = "CropCircleTransformation"
}