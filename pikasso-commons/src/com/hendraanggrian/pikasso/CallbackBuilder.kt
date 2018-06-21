package com.hendraanggrian.pikasso

import java.lang.Exception

interface CallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: () -> Unit)

    /** Invoked when image failed to load. */
    fun onError(callback: (e: Exception) -> Unit)
}