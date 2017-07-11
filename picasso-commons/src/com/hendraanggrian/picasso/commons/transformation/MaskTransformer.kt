package com.hendraanggrian.picasso.commons.transformation

import android.graphics.*
import android.graphics.drawable.Drawable
import android.os.Bundle
import java.lang.ref.WeakReference

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class MaskTransformer(private val mask: Drawable) : Transformer() {

    companion object {
        private var paint: WeakReference<Paint>? = null
    }

    override fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap {
        val width = source.width
        val height = source.height
        val result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(result)
        mask.setBounds(0, 0, width, height)
        mask.draw(canvas)
        canvas.drawBitmap(source, 0f, 0f, getPaint())

        if (shouldRecycle) {
            source.recycle()
        }
        return result
    }

    override fun keyBundle(): Bundle {
        val bundle = Bundle()
        bundle.putString(Transformer.KEY_NAME, "MaskTransformer")
        return bundle
    }

    private fun getPaint(): Paint {
        var instance: Paint?
        if (paint != null) {
            instance = paint!!.get()
            if (instance != null) {
                return instance
            }
        }
        instance = Paint()
        instance.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        paint = WeakReference(instance)
        return instance
    }
}