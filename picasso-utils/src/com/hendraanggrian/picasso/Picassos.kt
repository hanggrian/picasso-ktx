@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.picasso

import android.app.Fragment
import android.content.Context
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import com.squareup.picasso.Picasso.with
import com.squareup.picasso.RequestCreator
import java.io.File

inline fun Context.picasso(file: File?): RequestCreator = with(this).load(file)
inline fun Context.picasso(path: String?): RequestCreator = with(this).load(path)
inline fun Context.picasso(resourceId: Int): RequestCreator = with(this).load(resourceId)
inline fun Context.picasso(uri: Uri?): RequestCreator = with(this).load(uri)

inline fun Fragment.picasso(file: File?): RequestCreator = with(if (SDK_INT < 23) activity else context).load(file)
inline fun Fragment.picasso(path: String?): RequestCreator = with(if (SDK_INT < 23) activity else context).load(path)
inline fun Fragment.picasso(resourceId: Int): RequestCreator = with(if (SDK_INT < 23) activity else context).load(resourceId)
inline fun Fragment.picasso(uri: Uri?): RequestCreator = with(if (SDK_INT < 23) activity else context).load(uri)