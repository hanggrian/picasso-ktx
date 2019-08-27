package com.hendraanggrian.pikasso.transformations

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

@PublishedApi
internal class CropRoundedTransformation(
    private val radius: Float,
    private val margin: Float
) : Transformation {

    constructor(radius: Int, margin: Int) : this(radius.toFloat(), margin.toFloat())

    override fun transform(source: Bitmap): Bitmap {
        val target = createBitmap(source.width, source.height, ARGB_8888)
        Canvas(target).drawRoundRect(RectF(margin, margin,
            (source.width - margin),
            (source.height - margin)
        ), radius, radius, Paint(ANTI_ALIAS_FLAG).apply {
            isAntiAlias = true
            shader = BitmapShader(source, CLAMP, CLAMP)
        })
        source.recycle()
        return target
    }

    override fun key(): String = "CropRoundedTransformation(radius=$radius, margin=$margin)"
}
