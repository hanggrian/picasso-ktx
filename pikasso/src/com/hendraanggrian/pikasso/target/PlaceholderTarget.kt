package com.hendraanggrian.pikasso.target

import android.animation.LayoutTransition
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

/** Modified [Target] that displays custom view when image is loading. */
class PlaceholderTarget(private val target: ImageView, placeholder: View) : Target {

    private companion object {
        const val TAG = "PlaceholderTarget"
    }

    private val placeholderLayout = FrameLayout(target.context)

    init {
        target.tag = this
        placeholderLayout.layoutParams = target.layoutParams
        placeholderLayout.addView(placeholder)
        placeholderLayout.tag = TAG
        transition(true)
    }

    override fun equals(other: Any?) = other != null &&
        other is PlaceholderTarget &&
        other.target == target

    override fun hashCode() = target.hashCode()

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        clearPlaceholders()
        target.isVisible = true
        target.setImageBitmap(bitmap)
    }

    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
        clearPlaceholders()
        target.isVisible = true
        target.setImageDrawable(errorDrawable)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        if (placeHolderDrawable != null) {
            placeholderLayout.addView(ImageView(target.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setImageDrawable(placeHolderDrawable)
            }, 0)
        }
        clearPlaceholders()
        targetParent.addView(placeholderLayout, targetParent.indexOfChild(target))
        target.isVisible = false
    }

    fun transition(enable: Boolean): PlaceholderTarget {
        when {
            enable && targetParent.layoutTransition == null ->
                targetParent.layoutTransition = LayoutTransition()
            !enable -> targetParent.layoutTransition = null
        }
        return this
    }

    private inline val targetParent: ViewGroup get() = target.parent as ViewGroup

    private fun clearPlaceholders() = targetParent.childs
        .filter { it.tag == TAG }
        .forEach { targetParent.removeView(it) }

    private inline val ViewGroup.childs: List<View>
        get() = (0 until childCount).map { getChildAt(it) }

    private inline var View.isVisible: Boolean
        get() = visibility == VISIBLE
        set(visible) {
            visibility = if (visible) VISIBLE else GONE
        }
}