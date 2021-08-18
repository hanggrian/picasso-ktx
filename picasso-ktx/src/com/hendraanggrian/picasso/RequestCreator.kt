package com.hendraanggrian.picasso

import android.app.Notification
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import com.hendraanggrian.picasso.internal.CropCircleTransformation
import com.hendraanggrian.picasso.internal.CropRoundedTransformation
import com.hendraanggrian.picasso.internal.CropSquareTransformation
import com.hendraanggrian.picasso.internal.GrayscaleTransformation
import com.hendraanggrian.picasso.internal.MaskTransformation
import com.hendraanggrian.picasso.internal.OverlayTransformation
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/** Transform image to square. */
fun RequestCreator.square(): RequestCreator =
    transform(CropSquareTransformation())

/** Transform image to circle. */
fun RequestCreator.circle(): RequestCreator =
    transform(CropCircleTransformation())

/** Transform image to rounded edge with defined radius and margin. */
fun RequestCreator.rounded(radius: Float, margin: Float): RequestCreator =
    transform(CropRoundedTransformation(radius, margin))

/** Transform image to rounded edge with defined radius and margin. */
@Suppress("NOTHING_TO_INLINE")
inline fun RequestCreator.rounded(radius: Int, margin: Int): RequestCreator =
    rounded(radius.toFloat(), margin.toFloat())

/** Transform image to overlay color, transparency may be applied. */
fun RequestCreator.overlay(@ColorInt color: Int): RequestCreator =
    transform(OverlayTransformation(color))

/** Transform image to grayscale color. */
fun RequestCreator.grayscale(): RequestCreator =
    transform(GrayscaleTransformation())

/** Transform image to masked. */
fun RequestCreator.mask(drawable: Drawable): RequestCreator =
    transform(MaskTransformation(drawable))

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
