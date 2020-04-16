package com.example.pikasso

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.view.GravityCompat.START
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_demo.*

class PersistentBottomSheetBehavior @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : BottomSheetBehavior<AppBarLayout>(context, attrs) {

    init {
        val activity = context as DemoActivity
        setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(view: View, i: Float) {}
            override fun onStateChanged(view: View, state: Int) = activity.run {
                when {
                    state == STATE_HIDDEN -> setState(STATE_COLLAPSED) // persistent
                    state == STATE_EXPANDED -> {
                        pasteItem.isVisible = true
                        toggleExpandItem.icon = getDrawable(this,
                            R.drawable.ic_collapse
                        )
                        toggleExpandItem.title = getString(R.string.collapse)
                    }
                    state == STATE_COLLAPSED -> {
                        pasteItem.isVisible = false
                        toggleExpandItem.icon = getDrawable(this,
                            R.drawable.ic_expand
                        )
                        toggleExpandItem.title = getString(R.string.expand)
                    }
                    activity.drawerLayout.isDrawerOpen(START) -> drawerLayout.closeDrawer(START)
                }
            }
        })
    }
}