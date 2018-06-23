package com.hendraanggrian.pikasso.demo

import android.content.Context
import android.support.annotation.AttrRes
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.TextView
import org.jetbrains.anko.textAppearance
import org.jetbrains.anko.textView

class PaletteDisplayView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val textView: TextView = textView {
        layoutParams = LayoutParams(MATCH_PARENT, MATCH_PARENT).apply { gravity = CENTER }
        textAppearance = R.style.TextAppearance_AppCompat_Medium
    }

    var text: CharSequence
        get() = textView.text
        set(value) {
            textView.text = value
        }
}