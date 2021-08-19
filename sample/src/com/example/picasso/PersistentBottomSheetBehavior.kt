package com.example.picasso

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.GravityCompat.START
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_example.*

class PersistentBottomSheetBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BottomSheetBehavior<AppBarLayout>(context, attrs) {

    init {
        val activity = context as ExampleActivity
        addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, i: Float) {}
            override fun onStateChanged(view: View, state: Int) = activity.run {
                when (state) {
                    STATE_HIDDEN -> setState(STATE_COLLAPSED) // persistent
                    STATE_EXPANDED -> {
                        pasteItem.isVisible = true
                        toggleExpandItem.icon = getDrawable(this, R.drawable.ic_collapse)
                        toggleExpandItem.title = "Collapse"
                    }
                    STATE_COLLAPSED -> {
                        pasteItem.isVisible = false
                        toggleExpandItem.icon = getDrawable(this, R.drawable.ic_expand)
                        toggleExpandItem.title = "Expand"
                    }
                }
            }
        })
    }
}