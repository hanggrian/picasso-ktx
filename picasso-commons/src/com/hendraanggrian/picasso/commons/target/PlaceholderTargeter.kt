package com.hendraanggrian.picasso.commons.target

import android.animation.LayoutTransition
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import com.hendraanggrian.support.utils.view.findViewsWithTag
import com.squareup.picasso.Picasso

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
internal class PlaceholderTargeter(private val target: ImageView, placeholderView: View) : Targeter() {

    companion object {
        private val TAG = "PlaceholderTargeter"
    }

    private val parent: ViewGroup = target.parent as ViewGroup
    private val placeholder: ViewGroup = FrameLayout(target.context)

    init {
        this.target.tag = this
        this.placeholder.layoutParams = target.layoutParams
        this.placeholder.addView(placeholderView)
        this.placeholder.tag = TAG
        transition(true)
    }

    override fun equals(other: Any?): Boolean = other != null && other is PlaceholderTargeter && other.target === target

    override fun hashCode(): Int = target.hashCode()

    override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
        for (view in parent.findViewsWithTag(TAG)) {
            parent.removeView(view)
        }
        target.visibility = View.VISIBLE
        target.setImageBitmap(bitmap)
        super.onBitmapLoaded(bitmap, from)
    }

    override fun onBitmapFailed(errorDrawable: Drawable?) {
        for (view in parent.findViewsWithTag(TAG)) {
            parent.removeView(view)
        }
        target.visibility = View.VISIBLE
        target.setImageDrawable(errorDrawable)
        super.onBitmapFailed(errorDrawable)
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        if (placeHolderDrawable != null) {
            val placeholderView = ImageView(target.context)
            placeholderView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            placeholderView.setImageDrawable(placeHolderDrawable)
            placeholder.addView(placeholderView, 0)
        }
        for (view in parent.findViewsWithTag(TAG)) {
            parent.removeView(view)
        }
        parent.addView(placeholder, parent.indexOfChild(target))
        target.visibility = View.GONE
        super.onPrepareLoad(placeHolderDrawable)
    }

    fun transition(enable: Boolean): PlaceholderTargeter {
        val parent = target.parent as ViewGroup
        if (Build.VERSION.SDK_INT >= 11) {
            if (enable && parent.layoutTransition == null) {
                parent.layoutTransition = LayoutTransition()
            } else if (!enable) {
                parent.layoutTransition = null
            }
        }
        return this
    }
}