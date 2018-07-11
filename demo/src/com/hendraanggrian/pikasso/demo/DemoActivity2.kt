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
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hendraanggrian.material.errorbar.Errorbar
import com.hendraanggrian.material.errorbar.indefiniteErrorbar
import com.hendraanggrian.pikasso.buildPicasso
import com.hendraanggrian.pikasso.palette.palette
import com.hendraanggrian.pikasso.transformations.circle
import com.hendraanggrian.pikasso.transformations.grayscale
import com.hendraanggrian.pikasso.transformations.overlay
import com.hendraanggrian.pikasso.transformations.rounded
import com.hendraanggrian.pikasso.transformations.square
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_demo2.*

class DemoActivity2 : AppCompatActivity(), /*SlidingPaneLayout.PanelSlideListener,*/
    OnSharedPreferenceChangeListener {

    private val fragment = DemoFragment()

    lateinit var picasso: Picasso
    lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var pasteItem: MenuItem
    lateinit var toggleExpandItem: MenuItem
    lateinit var errorbar: Errorbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo2)
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
            fragment.input.key)
        // panelLayout.setScrollableView(fragment.view)
        // panelLayout.addPanelSlideListener(this)

        toolbar2.inflateMenu(R.menu.activity_demo)
        pasteItem = toolbar2.menu.findItem(R.id.pasteItem).apply {
            setOnMenuItemClickListener {
                fragment.listView.smoothScrollToPosition(0)
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                    if (hasPrimaryClip() && primaryClipDescription
                            .hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                        val clipboard = primaryClip.getItemAt(0).text.toString()
                        fragment.input.text = clipboard
                        fragment.onPreferenceChange(fragment.input, clipboard) // trigger
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
            setAction(R.string.expand) {
                //                panelLayout.panelState = EXPANDED
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
//        if (item.itemId == android.R.id.home) drawerLayout.openDrawer(START)
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

    /*override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelStateChanged(panel: View, previousState: PanelState, state: PanelState) {
        when {
            state == EXPANDED -> {
                pasteItem.isVisible = true
                toggleExpandItem.icon = getDrawable(this@DemoActivity2, R.drawable.ic_collapse)
                toggleExpandItem.title = getString(R.string.collapse)
            }
            state == COLLAPSED -> {
                pasteItem.isVisible = false
                toggleExpandItem.icon = getDrawable(this@DemoActivity2, R.drawable.ic_expand)
                toggleExpandItem.title = getString(R.string.expand)
            }
            drawerLayout.isDrawerOpen(START) -> drawerLayout.closeDrawer(START)
        }
    }*/

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == fragment.input.key) {
//            button.isEnabled = WEB_URL.toRegex().matches(sharedPreferences.getString(key, ""))
        }
    }

    fun toggleExpand(@Suppress("UNUSED_PARAMETER") view: View) {
//        panelLayout.panelState = if (panelLayout.panelState == EXPANDED) COLLAPSED else EXPANDED
    }

    fun load(@Suppress("UNUSED_PARAMETER") view: View) {
        if (errorbar.isShown) errorbar.dismiss()
        toolbar2.title = getString(R.string.loading)
        progressBar.visibility = VISIBLE
//        panelLayout.panelState = COLLAPSED
        picasso.load(fragment.input.text)
            .apply {
                if (fragment.cropCircle.isChecked) circle()
                if (fragment.cropRounded.isChecked) rounded(25.px, 10.px)
                if (fragment.cropSquare.isChecked) square()
                if (fragment.grayscale.isChecked) grayscale()
                /*if (fragment.mask.isChecked) mask(getDrawable(this@DemoActivity2,
                    R.drawable.mask)!!)*/
                if (fragment.overlay.isChecked) overlay(RED)
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