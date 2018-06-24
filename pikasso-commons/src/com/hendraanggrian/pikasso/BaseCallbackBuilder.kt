package com.hendraanggrian.pikasso

import java.lang.Exception

interface BaseCallbackBuilder {

    /** Invoked when image failed to load. */
    fun onError(callback: (e: Exception) -> Unit)
}