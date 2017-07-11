package com.hendraanggrian.picasso.commons.transformation

import android.graphics.*
import android.os.Bundle

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class ColorGrayscaleTransformer : Transformer() {

    override fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap {
        val target = createDefaultBitmap(source)

        val saturation = ColorMatrix()
        saturation.setSaturation(0f)
        val paint = Paint()
        paint.colorFilter = ColorMatrixColorFilter(saturation)
        Canvas(target).drawBitmap(source, 0f, 0f, paint)

        if (shouldRecycle) {
            source.recycle()
        }
        return target
    }

    override fun keyBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(Transformer.KEY_NAME, "ColorGrayscaleTransformer")
        return bundle
    }
}