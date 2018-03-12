package com.hendraanggrian.pikasso.target

import android.animation.LayoutTransition
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso

class PlaceholderProgress(private val target: ImageView, placeholder: View) : ProgressTarget() {

    companion object {
        private const val TAG = "PlaceholderProgress"
    }

    private val placeholderLayout = FrameLayout(target.context)

    init {
        target.tag = this
        placeholderLayout.layoutParams = target.layoutParams
        placeholderLayout.addView(placeholder)
        placeholderLayout.tag = TAG
        transition(true)
    }

    override fun equals(other: Any?) = other != null && other is PlaceholderProgress && other.target == target
    override fun hashCode() = target.hashCode()

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        clearPlaceholders()
        target.isVisible = true
        target.setImageBitmap(bitmap)
        super.onBitmapLoaded(bitmap, from)
    }

    override fun onBitmapFailed(e: Exception, errorDrawable: Drawable?) {
        clearPlaceholders()
        target.isVisible = true
        target.setImageDrawable(errorDrawable)
        super.onBitmapFailed(e, errorDrawable)
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
        super.onPrepareLoad(placeHolderDrawable)
    }

    fun transition(enable: Boolean): PlaceholderProgress {
        when {
            enable && targetParent.layoutTransition == null -> targetParent.layoutTransition = LayoutTransition()
            !enable -> targetParent.layoutTransition = null
        }
        return this
    }

    private inline val targetParent: ViewGroup get() = target.parent as ViewGroup

    private fun clearPlaceholders() = targetParent.childs
        .filter { it.tag == TAG }
        .forEach { targetParent.removeView(it) }

    private inline val ViewGroup.childs: List<View> get() = (0 until childCount).map { getChildAt(it) }

    private inline var View.isVisible: Boolean
        get() = visibility == View.VISIBLE
        set(visible) {
            visibility = if (visible) View.VISIBLE else View.GONE
        }
}