@file:JvmName("Picassos")
@file:Suppress("NOTHING_TO_INLINE")

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

fun Picasso.getCache(): Cache = getInternalCache(this)

inline fun Context.picasso(file: File): RequestCreator = Picasso.with(this).load(file)
inline fun Context.picasso(path: String): RequestCreator = Picasso.with(this).load(path)
inline fun Context.picasso(resourceId: Int): RequestCreator = Picasso.with(this).load(resourceId)
inline fun Context.picasso(uri: Uri): RequestCreator = Picasso.with(this).load(uri)

inline fun Fragment.picasso(file: File): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(file)
inline fun Fragment.picasso(path: String): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(path)
inline fun Fragment.picasso(resourceId: Int): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(resourceId)
inline fun Fragment.picasso(uri: Uri): RequestCreator = Picasso.with(if (Build.VERSION.SDK_INT < 23) activity else context).load(uri)

inline fun android.support.v4.app.Fragment.picasso(file: File): RequestCreator = Picasso.with(context).load(file)
inline fun android.support.v4.app.Fragment.picasso(path: String): RequestCreator = Picasso.with(context).load(path)
inline fun android.support.v4.app.Fragment.picasso(resourceId: Int): RequestCreator = Picasso.with(context).load(resourceId)
inline fun android.support.v4.app.Fragment.picasso(uri: Uri): RequestCreator = Picasso.with(context).load(uri)