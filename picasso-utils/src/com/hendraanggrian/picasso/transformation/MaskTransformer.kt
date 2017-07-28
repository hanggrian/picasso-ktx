package com.hendraanggrian.picasso.transformation

import android.content.Context
import android.graphics.*
import android.support.annotation.DrawableRes
import com.hendraanggrian.kota.content.res.getDrawable2
import com.squareup.picasso.Transformation
import java.lang.ref.WeakReference

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class MaskTransformer(private val context: Context, @DrawableRes private val resId: Int) : Transformation {

    companion object {
        private var paint: WeakReference<Paint>? = null
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(target)
        val mask = context.getDrawable2(resId)
        mask.setBounds(0, 0, width, height)
        mask.draw(canvas)
        canvas.drawBitmap(source, 0f, 0f, getPaint())
        source.recycle()
        return target
    }

    override fun key() = context.resources.getResourceEntryName(resId).let { "MaskTransformer[maskId=$it]" }

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