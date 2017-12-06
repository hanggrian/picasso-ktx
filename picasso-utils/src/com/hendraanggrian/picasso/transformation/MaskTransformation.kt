package com.hendraanggrian.picasso.transformation

import android.content.Context
import android.graphics.*
import android.support.annotation.DrawableRes
import com.hendraanggrian.common.content.getDrawable2
import com.squareup.picasso.Transformation
import java.lang.ref.WeakReference

internal class MaskTransformation(private val context: Context, @DrawableRes private val resId: Int) : Transformation {

    companion object {
        private var PAINT: WeakReference<Paint>? = null
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val target = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(target)
        val mask = context.getDrawable2(resId)
        mask.setBounds(0, 0, width, height)
        mask.draw(canvas)
        canvas.drawBitmap(source, 0f, 0f, paint)
        source.recycle()
        return target
    }

    override fun key() = "MaskTransformation[maskId=${context.resources.getResourceEntryName(resId)}]"

    private val paint: Paint
        get() {
            var instance: Paint?
            if (PAINT != null) {
                instance = PAINT!!.get()
                if (instance != null) {
                    return instance
                }
            }
            instance = Paint()
            instance.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            PAINT = WeakReference(instance)
            return instance
        }
}