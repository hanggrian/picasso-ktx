package com.hendraanggrian.pikasso.demo

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.Configuration
import android.graphics.Color.RED
import android.graphics.Color.blue
import android.graphics.Color.green
import android.graphics.Color.red
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.design.widget.Errorbar
import android.support.design.widget.indefiniteErrorbar
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v4.util.PatternsCompat.WEB_URL
import android.support.v4.view.GravityCompat.START
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.hendraanggrian.pikasso.buildPicasso
import com.hendraanggrian.pikasso.palette.palette
import com.hendraanggrian.pikasso.transformations.circle
import com.hendraanggrian.pikasso.transformations.grayscale
import com.hendraanggrian.pikasso.transformations.mask
import com.hendraanggrian.pikasso.transformations.overlay
import com.hendraanggrian.pikasso.transformations.rounded
import com.hendraanggrian.pikasso.transformations.square
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelSlideListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED
import com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_demo.*
import org.jetbrains.anko.dip

class DemoActivity : AppCompatActivity(), PanelSlideListener, OnSharedPreferenceChangeListener {

    private val fragment = DemoFragment()

    lateinit var picasso: Picasso
    lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var pasteItem: MenuItem
    lateinit var toggleExpandItem: MenuItem
    lateinit var errorbar: Errorbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)
        supportActionBar!!.run {
            title = ""
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        picasso = buildPicasso {
            loggingEnabled(BuildConfig.DEBUG)
            memoryCache(Cache.NONE)
        }
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0).apply {
            isDrawerIndicatorEnabled = true
        }
        drawerLayout.addDrawerListener(drawerToggle)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commitNow()
        onSharedPreferenceChanged(fragment.preferenceScreen.sharedPreferences,
            fragment.inputPreference.key)
        panelLayout.setScrollableView(fragment.view)
        panelLayout.addPanelSlideListener(this)

        toolbar2.inflateMenu(R.menu.activity_demo)
        pasteItem = toolbar2.menu.findItem(R.id.pasteItem).apply {
            setOnMenuItemClickListener {
                fragment.listView.smoothScrollToPosition(0)
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                    if (hasPrimaryClip() && primaryClipDescription
                            .hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                        val clipboard = primaryClip.getItemAt(0).text.toString()
                        fragment.inputPreference.text = clipboard
                        fragment.onPreferenceChange(fragment.inputPreference, clipboard) // trigger
                    }
                }
                true
            }
        }
        toggleExpandItem = toolbar2.menu.findItem(R.id.toggleExpandItem).apply {
            setOnMenuItemClickListener {
                toolbar2.performClick()
                true
            }
        }

        errorbar = photoView.indefiniteErrorbar("Expand panel below to start loading") {
            setBackground(R.drawable.errorbar_bg_cloud)
            setIcon(R.drawable.errorbar_ic_cloud)
            setAction(R.string.expand) {
                panelLayout.panelState = EXPANDED
            }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) drawerLayout.openDrawer(START)
        return false
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelStateChanged(panel: View, previousState: PanelState, state: PanelState) {
        when {
            state == EXPANDED -> {
                pasteItem.isVisible = true
                toggleExpandItem.icon = getDrawable(this@DemoActivity, R.drawable.ic_collapse)
                toggleExpandItem.title = getString(R.string.collapse)
            }
            state == COLLAPSED -> {
                pasteItem.isVisible = false
                toggleExpandItem.icon = getDrawable(this@DemoActivity, R.drawable.ic_expand)
                toggleExpandItem.title = getString(R.string.expand)
            }
            drawerLayout.isDrawerOpen(START) -> drawerLayout.closeDrawer(START)
        }
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == fragment.inputPreference.key) {
            button.isEnabled = WEB_URL.toRegex().matches(sharedPreferences.getString(key, ""))
        }
    }

    fun toggleExpand(@Suppress("UNUSED_PARAMETER") view: View) {
        panelLayout.panelState = if (panelLayout.panelState == EXPANDED) COLLAPSED else EXPANDED
    }

    fun load(@Suppress("UNUSED_PARAMETER") view: View) {
        if (errorbar.isShown) errorbar.dismiss()
        toolbar2.title = getString(R.string.loading)
        progressBar.visibility = VISIBLE
        panelLayout.panelState = COLLAPSED
        picasso.load(fragment.inputPreference.text)
            .apply {
                if (fragment.cropCirclePreference.isChecked) circle()
                if (fragment.cropRoundedPreference.isChecked) rounded(dip(25), dip(10))
                if (fragment.cropSquarePreference.isChecked) square()
                if (fragment.grayscalePreference.isChecked) grayscale()
                if (fragment.maskPreference.isChecked) mask(getDrawable(this@DemoActivity,
                    R.drawable.mask)!!)
                if (fragment.overlayPreference.isChecked) overlay(RED)
            }
            .palette(photoView) {
                onSuccess {
                    useVibrant { vibrantToolbar assign it }
                    useLightVibrant { lightVibrantToolbar assign it }
                    useDarkVibrant { darkVibrantToolbar assign it }
                    useMuted { mutedToolbar assign it }
                    useLightMuted { lightMutedToolbar assign it }
                    useDarkMuted { darkMutedToolbar assign it }
                    useDominant { dominantToolbar assign it }
                    toolbar2.title = getString(R.string.success)
                    progressBar.visibility = GONE
                }
                onError {
                    toolbar2.title = getString(R.string.error)
                    progressBar.visibility = GONE
                }
            }
    }

    private companion object {

        infix fun Toolbar.assign(@ColorInt color: Int) {
            setBackgroundColor(color)
            subtitle = "#%06X".format(0xFFFFFF and color)

            if ((red(color) + green(color) + blue(color)) / 3 < 127.5) {
                setTitleTextAppearance(context, R.style.TextAppearance_AppCompat_Small)
                setSubtitleTextAppearance(context, R.style.TextAppearance_AppCompat_Subhead)
            } else {
                setTitleTextAppearance(context, R.style.TextAppearance_AppCompat_Small_Inverse)
                setSubtitleTextAppearance(context, R.style.TextAppearance_AppCompat_Subhead_Inverse)
            }
        }
    }
}