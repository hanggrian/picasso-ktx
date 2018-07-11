package com.hendraanggrian.pikasso.demo

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior

class PersistentBottomSheetBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BottomSheetBehavior<AppBarLayout>(context, attrs) {

    init {
        setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, i: Float) {}
            override fun onStateChanged(view: View, state: Int) {
                if (state == BottomSheetBehavior.STATE_HIDDEN) {
                    setState(BottomSheetBehavior.STATE_COLLAPSED)
                }
            }
        })
    }
}