package com.hendraanggrian.picasso.commons.transformation

import android.graphics.*
import android.os.Bundle
import android.support.annotation.ColorInt

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class ColorOverlayTransformer(@ColorInt private val color: Int) : Transformer() {

    override fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap {
        val target = createDefaultBitmap(source)

        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        Canvas(target).drawBitmap(source, 0f, 0f, paint)

        if (shouldRecycle) {
            source.recycle()
        }
        return target
    }

    override fun keyBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(Transformer.KEY_NAME, "ColorOverlayTransformer")
        bundle.putInt("color", color)
        return bundle
    }
}