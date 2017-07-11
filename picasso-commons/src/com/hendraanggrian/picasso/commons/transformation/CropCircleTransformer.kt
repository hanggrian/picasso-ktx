package com.hendraanggrian.picasso.commons.transformation

import android.graphics.*
import android.os.Bundle

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class CropCircleTransformer : CropSquareTransformer() {

    override fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap {
        val r = Math.min(source.width, source.height) / 2f
        val squared = super.transform(source, shouldRecycle)
        val circle = createDefaultBitmap(source)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        Canvas(circle).drawCircle(r, r, r, paint)

        if (shouldRecycle) {
            squared.recycle()
        }
        return circle
    }

    override fun keyBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(Transformer.KEY_NAME, "CropCircleTransformer")
        return bundle
    }
}