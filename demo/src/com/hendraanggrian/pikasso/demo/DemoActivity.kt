package com.hendraanggrian.pikasso.demo

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Color.RED
import android.os.Bundle
import android.support.design.widget.Errorbar
import android.support.design.widget.indefiniteErrorbar
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v4.util.PatternsCompat.WEB_URL
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.hendraanggrian.pikasso.buildPicasso
import com.hendraanggrian.pikasso.demo.BuildConfig.DEBUG
import com.hendraanggrian.pikasso.into
import com.hendraanggrian.pikasso.placeholders.toHorizontalProgressTarget
import com.hendraanggrian.pikasso.placeholders.toProgressTarget
import com.hendraanggrian.pikasso.transformations.CropCircleTransformation
import com.hendraanggrian.pikasso.transformations.CropRoundedTransformation
import com.hendraanggrian.pikasso.transformations.CropSquareTransformation
import com.hendraanggrian.pikasso.transformations.GrayscaleTransformation
import com.hendraanggrian.pikasso.transformations.MaskTransformation
import com.hendraanggrian.pikasso.transformations.OverlayTransformation
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED
import com.squareup.picasso.Cache.NONE
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import kotlinx.android.synthetic.main.activity_demo.*
import org.jetbrains.anko.dip

class DemoActivity : AppCompatActivity(), PanelSlideListener, OnSharedPreferenceChangeListener {

    lateinit var picasso: Picasso
    lateinit var errorbar: Errorbar

    lateinit var fragment: DemoFragment
    lateinit var pasteItem: MenuItem
    lateinit var toggleExpandItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)

        picasso = buildPicasso {
            loggingEnabled(DEBUG)
            memoryCache(NONE)
        }

        fragment = DemoFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment.also {
                panelLayout.setScrollableView(it.view)
            })
            .commitNow()
        onSharedPreferenceChanged(fragment.preferenceScreen.sharedPreferences,
            fragment.inputPreference.key)
        panelLayout.addPanelSlideListener(this)

        errorbar = photoView.indefiniteErrorbar("Slide up panel below to start loading") {
            setBackground(R.drawable.errorbar_bg_cloud)
            setIcon(R.drawable.errorbar_ic_cloud)
        }
    }

    override fun onResume() {
        super.onResume()
        fragment.preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        fragment.preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_demo, menu)
        pasteItem = menu.findItem(R.id.pasteItem)
        toggleExpandItem = menu.findItem(R.id.toggleExpandItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {
            pasteItem -> {
                fragment.listView.smoothScrollToPosition(0)
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                    if (hasPrimaryClip() && primaryClipDescription
                            .hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                        val clipboard = primaryClip.getItemAt(0).text.toString()
                        fragment.inputPreference.text = clipboard
                        fragment.onPreferenceChange(fragment.inputPreference, clipboard) // trigger
                    }
                }
            }
            toggleExpandItem -> toolbar.performClick()
        }
        return true
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {}

    override fun onPanelStateChanged(panel: View, previousState: PanelState, state: PanelState) {
        if (state == EXPANDED) {
            pasteItem.isVisible = true
            toggleExpandItem.icon = getDrawable(this@DemoActivity, R.drawable.ic_collapse)
            toggleExpandItem.title = getString(R.string.collapse)
        } else if (state == COLLAPSED) {
            pasteItem.isVisible = false
            toggleExpandItem.icon = getDrawable(this@DemoActivity, R.drawable.ic_expand)
            toggleExpandItem.title = getString(R.string.expand)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == fragment.inputPreference.key) {
            button.isEnabled = WEB_URL.toRegex().matches(sharedPreferences.getString(key, null))
        }
    }

    fun toggleExpand(@Suppress("UNUSED_PARAMETER") view: View) {
        panelLayout.panelState = if (panelLayout.panelState == EXPANDED) COLLAPSED else EXPANDED
    }

    fun load(@Suppress("UNUSED_PARAMETER") view: View) {
        if (errorbar.isShown) errorbar.dismiss()
        toolbar.title = getString(R.string.loading)
        panelLayout.panelState = COLLAPSED
        val request = picasso.load(fragment.inputPreference.text)
            .transform(mutableListOf<Transformation>().also {
                if (fragment.cropCirclePreference.isChecked) it += CropCircleTransformation()
                if (fragment.cropRoundedPreference.isChecked) it +=
                    CropRoundedTransformation(dip(25), dip(10))
                if (fragment.cropSquarePreference.isChecked) it += CropSquareTransformation()
                if (fragment.grayscalePreference.isChecked) it += GrayscaleTransformation()
                if (fragment.maskPreference.isChecked) it +=
                    MaskTransformation(getDrawable(this, R.drawable.mask)!!)
                if (fragment.overlayPreference.isChecked) it += OverlayTransformation(RED)
            })
        when (fragment.placeholdersPreference.value) {
            "none" -> request.into(photoView) {
                onSuccess {
                    toolbar.title = getString(R.string.success)
                }
                onError {
                    toolbar.title = getString(R.string.error)
                }
            }
            "progress" -> request.into(photoView.toProgressTarget())
            "horizontalProgress" -> request.into(photoView.toHorizontalProgressTarget())
        }
    }
}