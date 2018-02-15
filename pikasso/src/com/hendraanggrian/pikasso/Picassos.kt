@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.app.Fragment
import android.content.Context
import android.net.Uri
import android.support.annotation.DrawableRes
import com.squareup.picasso.Picasso.with
import com.squareup.picasso.RequestCreator
import java.io.File

inline fun Context.picasso(file: File?): RequestCreator = with(this).load(file)
inline fun Context.picasso(path: String?): RequestCreator = with(this).load(path)
inline fun Context.picasso(@DrawableRes resId: Int): RequestCreator = with(this).load(resId)
inline fun Context.picasso(uri: Uri?): RequestCreator = with(this).load(uri)

inline fun Fragment.picasso(file: File?): RequestCreator = with(activity).load(file)
inline fun Fragment.picasso(path: String?): RequestCreator = with(activity).load(path)
inline fun Fragment.picasso(@DrawableRes resId: Int): RequestCreator = with(activity).load(resId)
inline fun Fragment.picasso(uri: Uri?): RequestCreator = with(activity).load(uri)