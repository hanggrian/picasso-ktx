package com.hendraanggrian.picasso.commons.transformation

import android.graphics.*
import android.os.Bundle

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class CropRoundedTransformer(private val radius: Int, private val margin: Int) : Transformer() {

    override fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap {
        val right = (source.width - margin).toFloat()
        val bottom = (source.height - margin).toFloat()
        val target = createDefaultBitmap(source)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        Canvas(target).drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), right, bottom), radius.toFloat(), radius.toFloat(), paint)

        if (shouldRecycle) {
            source.recycle()
        }
        return target
    }

    override fun keyBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(Transformer.KEY_NAME, "CropRoundedTransformer")
        bundle.putInt("margin", margin)
        bundle.putInt("radius", radius)
        return bundle
    }
}