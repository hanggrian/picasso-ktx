package com.hendraanggrian.pikasso

import com.squareup.picasso.Callback

/** Partial callback builder, extended in `pikasso-palette` as well. */
interface BaseCallbackBuilder {

    /** Invoked when image failed to load. */
    fun onError(onError: (e: Exception) -> Unit)
}

/**
 * Interface to build [Callback] with Kotlin DSL.
 *
 * @see fetch
 * @see into
 */
interface CallbackBuilder : BaseCallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(onSuccess: () -> Unit)
}

internal class CallbackImpl : Callback, CallbackBuilder {
    private var _onSuccess: (() -> Unit)? = null
    private var _onError: ((Exception) -> Unit)? = null

    override fun onSuccess(onSuccess: () -> Unit) {
        _onSuccess = onSuccess
    }

    override fun onSuccess() {
        _onSuccess?.invoke()
    }

    override fun onError(onError: (e: Exception) -> Unit) {
        _onError = onError
    }

    override fun onError(e: Exception) {
        _onError?.invoke(e)
    }
}
