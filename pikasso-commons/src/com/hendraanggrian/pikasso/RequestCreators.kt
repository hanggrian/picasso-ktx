package com.hendraanggrian.pikasso

import android.app.Notification
import android.support.annotation.IdRes
import android.widget.ImageView
import android.widget.RemoteViews
import com.hendraanggrian.pikasso.internal._Callback
import com.hendraanggrian.pikasso.internal._Target
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/**
 * Completes the request without target while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.fetch
 */
inline fun RequestCreator.fetch(
    builder: CallbackBuilder.() -> Unit
): Unit = fetch(_Callback().apply(builder))

/**
 * Completes the request into an [ImageView] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    target: ImageView,
    callback: CallbackBuilder.() -> Unit
): Unit = into(target, _Callback().apply(callback))

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
): Unit = into(remoteViews, viewId, notificationId, notification, notificationTag,
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
): Unit = into(remoteViews, viewId, appWidgetIds, _Callback().apply(callback))

/**
 * Completes the request into a [Target] with Kotlin DSL, returning the [Target] created.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    builder: TargetBuilder.() -> Unit
): Target = _Target().also {
    it.builder()
    into(it)
}