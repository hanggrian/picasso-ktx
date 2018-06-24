package com.hendraanggrian.pikasso

interface CallbackBuilder : BaseCallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: () -> Unit)
}