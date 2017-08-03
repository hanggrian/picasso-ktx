@file:JvmName("Picassos")

package com.hendraanggrian.picasso

import android.app.Fragment
import android.content.Context
import android.net.Uri
import android.os.Build
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.getInternalCache
import java.io.File

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */

fun Picasso.getCache(): Cache = getInternalCache(this) // memory cache, not storage

fun Context.picasso(file: File): RequestCreator = Picasso.with(this).load(file)
fun Context.picasso(path: String): RequestCreator = Picasso.with(this).load(path)
fun Context.picasso(resourceId: Int): RequestCreator = Picasso.with(this).load(resourceId)
fun Context.picasso(uri: Uri): RequestCreator = Picasso.with(this).load(uri)

fun Fragment.picasso(file: File): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(file)
fun Fragment.picasso(path: String): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(path)
fun Fragment.picasso(resourceId: Int): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(resourceId)
fun Fragment.picasso(uri: Uri): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(uri)

fun android.support.v4.app.Fragment.picasso(file: File): RequestCreator = Picasso.with(context).load(file)
fun android.support.v4.app.Fragment.picasso(path: String): RequestCreator = Picasso.with(context).load(path)
fun android.support.v4.app.Fragment.picasso(resourceId: Int): RequestCreator = Picasso.with(context).load(resourceId)
fun android.support.v4.app.Fragment.picasso(uri: Uri): RequestCreator = Picasso.with(context).load(uri)