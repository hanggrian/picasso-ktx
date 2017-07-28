package com.hendraanggrian.picasso.transformation

import android.graphics.*
import com.squareup.picasso.Transformation

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class CropRoundedTransformer(private val radius: Int, private val margin: Int) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val right = (source.width - margin).toFloat()
        val bottom = (source.height - margin).toFloat()
        val target = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        Canvas(target).drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), Paint(Paint.ANTI_ALIAS_FLAG).apply {
            shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        })
        source.recycle()
        return target
    }

    override fun key() = "CropRoundedTransformer[margin=$margin, radius=$radius]"
}