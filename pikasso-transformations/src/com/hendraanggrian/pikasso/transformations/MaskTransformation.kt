package com.hendraanggrian.pikasso.transformations

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.SRC_IN
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.Drawable
import com.squareup.picasso.Transformation

class MaskTransformation(private val drawable: Drawable) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val target = createBitmap(width, height, ARGB_8888)
        Canvas(target).run {
            drawable.apply { setBounds(0, 0, width, height) }.draw(this)
            drawBitmap(source, 0f, 0f, Paint().apply { xfermode = PorterDuffXfermode(SRC_IN) })
        }
        source.recycle()
        return target
    }

    override fun key() = "MaskTransformation()"
}