package com.squareup.picasso

import android.content.Context
import android.net.Uri
import android.support.annotation.AnyRes
import java.io.File

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
object Picassos {
    val TAG = "Picassos"
    var isDebug: Boolean = false

    fun getCache(picasso: Picasso): Cache = picasso.cache
}

fun Picasso.getCache(): Cache = cache

fun Context.load(uri: Uri): RequestCreator = Picasso.with(this).load(uri)
fun Context.load(path: String): RequestCreator = Picasso.with(this).load(path)
fun Context.load(file: File): RequestCreator = Picasso.with(this).load(file)
fun Context.load(@AnyRes resourceId: Int): RequestCreator = Picasso.with(this).load(resourceId)