package com.hendraanggrian.picasso.transformation

import android.graphics.*
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.Shader.TileMode.CLAMP
import com.squareup.picasso.Transformation

internal class CropRoundedTransformation(private val radius: Int, private val margin: Int) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val right = (source.width - margin).toFloat()
        val bottom = (source.height - margin).toFloat()
        val target = createBitmap(source.width, source.height, ARGB_8888)
        Canvas(target).drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), Paint(ANTI_ALIAS_FLAG).apply {
            shader = BitmapShader(source, CLAMP, CLAMP)
        })
        source.recycle()
        return target
    }

    override fun key() = "CropRoundedTransformation[margin=$margin, radius=$radius]"
}