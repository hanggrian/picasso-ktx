package com.hendraanggrian.pikasso.transformation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff.Mode.SRC_IN
import android.graphics.PorterDuffXfermode
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat.getDrawable
import com.squareup.picasso.Transformation
import java.lang.ref.WeakReference

internal class MaskTransformation(private val context: Context, @DrawableRes private val resId: Int) : Transformation {

    companion object {
        private var PAINT: WeakReference<Paint>? = null
    }

    override fun transform(source: Bitmap): Bitmap {
        val width = source.width
        val height = source.height
        val target = createBitmap(width, height, ARGB_8888)
        val canvas = Canvas(target)
        val mask = getDrawable(context, resId)!!
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
            instance.xfermode = PorterDuffXfermode(SRC_IN)
            PAINT = WeakReference(instance)
            return instance
        }
}