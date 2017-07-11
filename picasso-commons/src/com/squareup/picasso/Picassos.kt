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
}

fun Context.picassoCache(): Cache = Picasso.with(this).cache

fun Context.picassoLoad(uri: Uri): RequestCreator = Picasso.with(this).load(uri)
fun Context.picassoLoad(path: String): RequestCreator = Picasso.with(this).load(path)
fun Context.picassoLoad(file: File): RequestCreator = Picasso.with(this).load(file)
fun Context.picassoLoad(@AnyRes resourceId: Int): RequestCreator = Picasso.with(this).load(resourceId)