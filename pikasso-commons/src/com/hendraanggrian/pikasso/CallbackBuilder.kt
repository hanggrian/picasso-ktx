package com.hendraanggrian.pikasso

import com.squareup.picasso.Callback
import java.lang.Exception

/** Interface to create [Callback] with Kotlin DSL. */
interface CallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: () -> Unit)

    /** Invoked when image failed to load. */
    fun onError(callback: (e: Exception) -> Unit)
}