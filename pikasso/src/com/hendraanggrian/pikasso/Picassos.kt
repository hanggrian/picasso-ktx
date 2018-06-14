@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.pikasso

import com.squareup.picasso.Picasso

/** The global [Picasso] instance. */
inline val picasso: Picasso get() = Picasso.get()