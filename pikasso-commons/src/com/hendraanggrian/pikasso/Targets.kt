package com.hendraanggrian.pikasso

import com.hendraanggrian.pikasso.internal._Target
import com.squareup.picasso.RequestCreator
import com.squareup.picasso.Target

/**
 * Completes the request into a [Target] with Kotlin DSL, returning the [Target] created.
 *
 * @see RequestCreator.into
 */
inline fun RequestCreator.into(
    builder: TargetBuilder.() -> Unit
): Target {
    val target = _Target().apply(builder)
    into(target)
    return target
}