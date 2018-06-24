package com.hendraanggrian.pikasso.transformations.internal

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Bitmap.createBitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.PorterDuff.Mode.SRC_ATOP
import android.graphics.PorterDuffColorFilter
import android.support.annotation.ColorInt
import com.squareup.picasso.Transformation

@PublishedApi
internal class OverlayTransformation(@ColorInt private val color: Int) : Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val target = createBitmap(source.width, source.height, ARGB_8888)
        Canvas(target).drawBitmap(source, 0f, 0f, Paint(ANTI_ALIAS_FLAG).apply {
            isAntiAlias = true
            colorFilter = PorterDuffColorFilter(color, SRC_ATOP)
        })
        source.recycle()
        return target
    }

    override fun key(): String = "OverlayTransformation(color=$color)"
}