package com.hendraanggrian.pikasso.transformation

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.RectF
import android.graphics.Shader.TileMode.CLAMP
import com.squareup.picasso.Transformation

class CropRoundedTransformation(
    private val radius: Int,
    private val margin: Int
) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val target = createBitmap(source.width, source.height, ARGB_8888)
        Canvas(target).drawRoundRect(RectF(
            margin.toFloat(),
            margin.toFloat(),
            (source.width - margin).toFloat(),
            (source.height - margin).toFloat()
        ), radius.toFloat(), radius.toFloat(), Paint(ANTI_ALIAS_FLAG).apply {
            isAntiAlias = true
            shader = BitmapShader(source, CLAMP, CLAMP)
        })
        source.recycle()
        return target
    }

    override fun key() = "CropRoundedTransformation(radius=$radius, margin=$margin)"
}