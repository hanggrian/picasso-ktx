package com.hendraanggrian.picasso.commons.transformation

import android.graphics.Bitmap
import android.os.Bundle

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal open class CropSquareTransformer : Transformer() {

    override fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap {
        val size = Math.min(source.width, source.height)
        val target = Bitmap.createBitmap(source,
                (source.width - size) / 2,
                (source.height - size) / 2,
                size,
                size)

        if (source != target && shouldRecycle) {
            source.recycle()
        }
        return target
    }

    override fun keyBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(Transformer.KEY_NAME, "CropSquareTransformer")
        return bundle
    }
}