package com.hendraanggrian.pikasso

import android.app.Notification
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.annotation.IdRes
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/**
 * Completes the request without target while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.fetch
 */
fun RequestCreator.fetch(builder: CallbackBuilder.() -> Unit): Unit =
    fetch(CallbackImpl().apply(builder))

/**
 * Completes the request into an [ImageView] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
fun RequestCreator.into(target: ImageView, callback: CallbackBuilder.() -> Unit): Unit =
    into(target, CallbackImpl().apply(callback))

/**
 * Completes the request into a [Notification] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
fun RequestCreator.into(
    remoteViews: RemoteViews,
    @IdRes viewId: Int,
    notificationId: Int,
    notification: Notification,
    notificationTag: String?,
    callback: CallbackBuilder.() -> Unit
): Unit = into(
    remoteViews,
    viewId,
    notificationId,
    notification,
    notificationTag,
    CallbackImpl().apply(callback)
)

/**
 * Completes the request into a [RemoteViews] while listening to its callback with Kotlin DSL.
 *
 * @see RequestCreator.into
 */
fun RequestCreator.into(
    remoteViews: RemoteViews,
    @IdRes viewId: Int,
    appWidgetIds: IntArray,
    callback: CallbackBuilder.() -> Unit
): Unit = into(remoteViews, viewId, appWidgetIds, CallbackImpl().apply(callback))

/**
 * Completes the request into a [Target] with Kotlin DSL, returning the [Target] created.
 *
 * @see RequestCreator.into
 */
fun RequestCreator.into(builder: TargetBuilder.() -> Unit): Target =
    TargetImpl().also {
        it.builder()
        into(it)
    }
