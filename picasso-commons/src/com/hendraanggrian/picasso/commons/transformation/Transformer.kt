package com.hendraanggrian.picasso.commons.transformation

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picassos
import com.squareup.picasso.Transformation

/**
 * Superclass of all <tt>Transformation</tt>.
 * In addition to function as <tt>Transformation</tt> for <tt>Picasso</tt>,
 * <tt>Transformer</tt> can also independently transform bitmap to bitmap, bitmap to drawable,
 * drawable to bitmap, and drawable to drawable using `toBitmap()` and `toDrawable().`
 *
 *
 * Do not explicitly trigger `transform()` as it will automatically recycle source bitmap,
 * `transform()` should only be invoked by <tt>Picasso</tt>.

 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
abstract class Transformer : Transformation {

    companion object {
        internal val KEY_NAME = "KEY_NAME"
    }

    protected abstract fun keyBundle(): Bundle

    /**
     * Logic of image transformation should happen here.
     */
    abstract fun transform(source: Bitmap, shouldRecycle: Boolean): Bitmap

    /**
     * Implemented from <tt>Transformation</tt> and should only be invoked by <tt>Picasso</tt>.
     * Calling this method outside <tt>Picasso</tt> will cause the source bitmap to be recycled and
     * likely to cause errors.
     */
    override fun transform(source: Bitmap): Bitmap = transform(source, true)

    override fun key(): String {
        val bundle = keyBundle()
        val name = bundle.getString(KEY_NAME)
        bundle.remove(KEY_NAME)
        var content = ""
        for (key in bundle.keySet()) {
            content += key + "=" + bundle.get(key) + ", "
        }
        if (content.endsWith(", ")) {
            content = content.substring(0, content.length - 2)
        }
        val key = "$name[$content]"
        if (Picassos.isDebug) Log.d(Picassos.TAG, key)
        return key
    }

    protected fun createDefaultBitmap(source: Bitmap): Bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
}