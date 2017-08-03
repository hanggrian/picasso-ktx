package com.hendraanggrian.picasso.target

import android.animation.LayoutTransition
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.hendraanggrian.kota.view.findAllViewsWithTag
import com.hendraanggrian.kota.view.removeAllViews
import com.squareup.picasso.Picasso

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class PlaceholderTargeter(private val target: ImageView, placeholderView: View) : Targeter() {

    companion object {
        private val TAG = "PlaceholderTargeter"
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

    override fun equals(other: Any?) = other != null && other is PlaceholderTargeter && other.target === target

    override fun hashCode() = target.hashCode()

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        rootView.removeAllViews(*rootView.findAllViewsWithTag(TAG).toTypedArray())
        target.visibility = View.VISIBLE
        target.setImageBitmap(bitmap)
        super.onBitmapLoaded(bitmap, from)
    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
        rootView.removeAllViews(*rootView.findAllViewsWithTag(TAG).toTypedArray())
        target.visibility = View.VISIBLE
        target.setImageDrawable(errorDrawable)
        super.onBitmapFailed(errorDrawable)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        if (placeHolderDrawable != null) {
            placeholderLayout.addView(ImageView(target.context).apply {
                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                setImageDrawable(placeHolderDrawable)
            }, 0)
        }
        rootView.removeAllViews(*rootView.findAllViewsWithTag(TAG).toTypedArray())
        rootView.addView(placeholderLayout, rootView.indexOfChild(target))
        target.visibility = View.GONE
        super.onPrepareLoad(placeHolderDrawable)
    }

    fun transition(enable: Boolean): PlaceholderTargeter {
        if (enable && rootView.layoutTransition == null) {
            rootView.layoutTransition = LayoutTransition()
        } else if (!enable) {
            rootView.layoutTransition = null
        }
        return this
    }
}