@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import android.content.Context
import android.net.Uri
import android.support.annotation.DrawableRes
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import java.io.File

/** The global [Picasso] instance. */
inline val picasso: Picasso get() = Picasso.get()

/** Build picasso instance using [Picasso.Builder] DSL. */
inline fun Context.buildPicasso(
    builder: Picasso.Builder.() -> Unit
): Picasso = Picasso.Builder(this).apply(builder).build()

/** Alias for [Picasso.load]. */
inline operator fun Picasso.invoke(uri: Uri?): RequestCreator = load(uri)

/** Alias for [Picasso.load]. */
inline operator fun Picasso.invoke(url: CharSequence?): RequestCreator = load(url?.toString())

/** Alias for [Picasso.load]. */
inline operator fun Picasso.invoke(file: File): RequestCreator = load(file)

/** Alias for [Picasso.load]. */
inline operator fun Picasso.invoke(@DrawableRes resourceId: Int): RequestCreator = load(resourceId)