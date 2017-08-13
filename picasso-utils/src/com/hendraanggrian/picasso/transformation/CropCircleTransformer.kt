package com.hendraanggrian.picasso.transformation

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class CropCircleTransformer : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val size = Math.min(source.width, source.height)
        val width = (source.width - size) / 2
        val height = (source.height - size) / 2
        val target = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        val r = size / 2f
        Canvas(target).drawCircle(r, r, r, Paint().apply {
            val shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            if (width != 0 || height != 0) {
                // source isn't square, move viewport to center
                val matrix = Matrix()
                matrix.setTranslate(-width.toFloat(), -height.toFloat())
                shader.setLocalMatrix(matrix)
            }
            this.shader = shader
            this.isAntiAlias = true
        })
        source.recycle()
        return target
    }

    override fun key() = "CropCircleTransformer"
}