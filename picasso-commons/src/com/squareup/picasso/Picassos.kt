package com.squareup.picasso

import android.content.Context

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
object Picassos {
    internal val TAG = "Picassos"
    var isDebug: Boolean = false
}

fun Context.getPicasso(): Picasso = Picasso.with(this)
fun Picasso.getCache(): Cache = cache // memory cache, not storage