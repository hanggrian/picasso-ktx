package com.hendraanggrian.pikasso

import android.app.Notification
import android.support.annotation.IdRes
import android.widget.ImageView
import android.widget.RemoteViews
import com.squareup.picasso.Callback
import com.squareup.picasso.RequestCreator
import java.lang.Exception

/**
 * Completes the request without target while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.fetch
 */
inline fun RequestCreator.fetch(callback: CallbackBuilder.() -> Unit) =
    fetch(_Callback().apply(callback))

/**
 * Completes the request into an [ImageView] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(target: ImageView, callback: CallbackBuilder.() -> Unit) =
    into(target, _Callback().apply(callback))

/**
 * Completes the request into a [Notification] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    remoteViews: RemoteViews,
    @IdRes viewId: Int,
    notificationId: Int,
    notification: Notification,
    notificationTag: String?,
    callback: CallbackBuilder.() -> Unit
) = into(remoteViews, viewId, notificationId, notification, notificationTag,
    _Callback().apply(callback))

/**
 * Completes the request into a [RemoteViews] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    remoteViews: RemoteViews,
    @IdRes viewId: Int,
    appWidgetIds: IntArray,
    callback: CallbackBuilder.() -> Unit
) = into(remoteViews, viewId, appWidgetIds, _Callback().apply(callback))

/** Interface to create [Callback] with Kotlin DSL. */
interface CallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: () -> Unit)

    /** Invoked when image failed to load. */
    fun onError(callback: (e: Exception) -> Unit)
}

@PublishedApi
internal class _Callback : Callback, CallbackBuilder {
    private var _onSuccess: (() -> Unit)? = null
    private var _onError: ((Exception) -> Unit)? = null

    override fun onSuccess(callback: () -> Unit) {
        _onSuccess = callback
    }

    override fun onSuccess() {
        _onSuccess?.invoke()
    }

    override fun onError(callback: (e: Exception) -> Unit) {
        _onError = callback
    }

    override fun onError(e: Exception) {
        _onError?.invoke(e)
    }
}