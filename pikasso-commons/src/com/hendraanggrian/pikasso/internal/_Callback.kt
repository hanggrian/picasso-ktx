package com.hendraanggrian.pikasso.internal

import com.hendraanggrian.pikasso.CallbackBuilder
import com.squareup.picasso.Callback
import java.lang.Exception

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