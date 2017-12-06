package com.hendraanggrian.picasso.target

import android.animation.LayoutTransition
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.ImageView
import com.squareup.picasso.Picasso

internal class PlaceholderTargeter(private val target: ImageView, placeholderView: View) : Targeter() {

    companion object {
        private const val TAG = "PlaceholderTargeter"
    }

    private val rootView = target.parent as ViewGroup
    private val placeholderLayout = FrameLayout(target.context)

    init {
        target.tag = this
        placeholderLayout.layoutParams = target.layoutParams
        placeholderLayout.addView(placeholderView)
        placeholderLayout.tag = TAG
        transition(true)
    }

    override fun equals(other: Any?) = other != null && other is PlaceholderTargeter && other.target == target

    override fun hashCode() = target.hashCode()

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        clearPlaceholderViews()
        target.setVisibleThen(true) { setImageBitmap(bitmap) }
        super.onBitmapLoaded(bitmap, from)
    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
        clearPlaceholderViews()
        target.setVisibleThen(true) { setImageDrawable(errorDrawable) }
        super.onBitmapFailed(errorDrawable)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        if (placeHolderDrawable != null) {
            placeholderLayout.addView(ImageView(target.context).apply {
                layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                setImageDrawable(placeHolderDrawable)
            }, 0)
        }
        clearPlaceholderViews()
        rootView.addView(placeholderLayout, rootView.indexOfChild(target))
        target.isVisible = false
        super.onPrepareLoad(placeHolderDrawable)
    }

    fun transition(enable: Boolean): PlaceholderTargeter {
        when {
            enable && rootView.layoutTransition == null -> rootView.layoutTransition = LayoutTransition()
            !enable -> rootView.layoutTransition = null
        }
        return this
    }

    private fun clearPlaceholderViews() = rootView.childs
            .filter { it.tag == TAG }
            .forEach { rootView.removeView(it) }
}