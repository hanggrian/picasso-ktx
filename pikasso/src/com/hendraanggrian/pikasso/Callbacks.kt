package com.hendraanggrian.pikasso

import android.app.Notification
import android.support.annotation.IdRes
import android.widget.ImageView
import android.widget.RemoteViews
import com.hendraanggrian.pikasso.internal._Callback
import com.squareup.picasso.Callback
import com.squareup.picasso.RequestCreator
import java.lang.Exception

/** Interface to create [Callback] with Kotlin DSL. */
interface CallbackBuilder {

    /** Invoked when image is successfully loaded. */
    fun onSuccess(callback: () -> Unit)

    /** Invoked when image failed to load. */
    fun onError(callback: (e: Exception) -> Unit)
}

/**
 * Completes the request without target while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.fetch
 */
inline fun RequestCreator.fetch(
    builder: CallbackBuilder.() -> Unit
) = fetch(_Callback().apply(builder))

/**
 * Completes the request into an [ImageView] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    target: ImageView,
    callback: CallbackBuilder.() -> Unit
) = into(target, _Callback().apply(callback))

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