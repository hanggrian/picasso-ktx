@file:Suppress("NOTHING_TO_INLINE")

package com.hendraanggrian.picasso.target

/** Mini version of [kota][https://github.com/hendraanggrian/kota] */

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup

internal inline val ViewGroup.childs: List<View> get() = (0 until childCount).map { getChildAt(it) }

internal inline var View.isVisible: Boolean
    get() = visibility == VISIBLE
    set(visible) {
        visibility = if (visible) VISIBLE else GONE
    }

internal inline fun <V : View> V.setVisibleThen(
        visible: Boolean,
        block: V.() -> Unit
) {
    isVisible = visible
    if (visibility == VISIBLE) block(this)
}