package com.hendraanggrian.pikasso

import com.squareup.picasso.Callback

interface BaseCallbackBuilder {

    /** Invoked when image failed to load. */
    fun onError(callback: (e: java.lang.Exception) -> Unit)
}

interface CallbackBuilder : BaseCallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: () -> Unit)
}

@PublishedApi
@Suppress("ClassName")
internal class _Callback : Callback, CallbackBuilder {
    private var onSuccess: (() -> Unit)? = null
    private var onError: ((Exception) -> Unit)? = null

    override fun onSuccess(callback: () -> Unit) {
        onSuccess = callback
    }

    override fun onSuccess() {
        onSuccess?.invoke()
    }

    override fun onError(callback: (e: Exception) -> Unit) {
        onError = callback
    }

    override fun onError(e: Exception) {
        onError?.invoke(e)
    }
}
