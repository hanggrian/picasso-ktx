@file:JvmName("Picassos")

package com.hendraanggrian.picasso

import android.content.Context
import android.net.Uri
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.getInternalCache
import java.io.File

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */

fun Context.picasso(file: File): RequestCreator = Picasso.with(this).load(file)

fun Context.picasso(path: String): RequestCreator = Picasso.with(this).load(path)

fun Context.picasso(resourceId: Int): RequestCreator = Picasso.with(this).load(resourceId)

fun Context.picasso(uri: Uri): RequestCreator = Picasso.with(this).load(uri)

fun Picasso.getCache(): Cache = getInternalCache(this) // memory cache, not storage