package com.squareup.picasso

import android.content.Context

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
object Picassos {
    val TAG = "Picassos"
    var isDebug: Boolean = false

    fun getCache(picasso: Picasso): Cache = picasso.cache
}

fun Context.getPicasso(): Picasso = Picasso.with(this)
fun Picasso.getCache(): Cache = cache