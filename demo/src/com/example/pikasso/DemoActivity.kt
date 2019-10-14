package com.example.pikasso

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorInt
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.util.PatternsCompat.WEB_URL
import androidx.core.view.GravityCompat.START
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.*
import com.hendraanggrian.material.errorbar.Errorbar
import com.hendraanggrian.material.errorbar.indefiniteErrorbar
import com.hendraanggrian.pikasso.buildPicasso
import com.hendraanggrian.pikasso.circle
import com.hendraanggrian.pikasso.grayscale
import com.hendraanggrian.pikasso.mask
import com.hendraanggrian.pikasso.overlay
import com.hendraanggrian.pikasso.palette
import com.hendraanggrian.pikasso.rounded
import com.hendraanggrian.pikasso.square
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    private val fragment = DemoFragment()
    private lateinit var sheetBehavior: BottomSheetBehavior<AppBarLayout>

    private lateinit var picasso: Picasso
    private lateinit var drawerToggle: ActionBarDrawerToggle

    lateinit var pasteItem: MenuItem
    lateinit var toggleExpandItem: MenuItem
    lateinit var errorbar: Errorbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        setSupportActionBar(toolbar)
        supportActionBar!!.run {
            title = null
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }

        picasso = buildPicasso {
            loggingEnabled = BuildConfig.DEBUG
            memoryCache = Cache.NONE
        }
        drawerToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0).apply {
            isDrawerIndicatorEnabled = true
        }
        drawerLayout.addDrawerListener(drawerToggle)

        sheetBehavior = from(appBarLayout)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commitNow()
        onSharedPreferenceChanged(fragment.preferenceScreen.sharedPreferences, fragment.input.key)

        toolbar2.inflateMenu(R.menu.activity_demo)
        pasteItem = toolbar2.menu.findItem(R.id.pasteItem).apply {
            setOnMenuItemClickListener {
                fragment.listView.smoothScrollToPosition(0)
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                    if (hasPrimaryClip() && primaryClipDescription!!
                            .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    ) {
                        val clipboard = primaryClip!!.getItemAt(0).text.toString()
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
                sheetBehavior.state = STATE_EXPANDED
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

    override fun onBackPressed() = when {
        sheetBehavior.state == STATE_EXPANDED -> sheetBehavior.state = STATE_COLLAPSED
        drawerLayout.isDrawerOpen(START) -> drawerLayout.closeDrawer(START)
        else -> super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawerLayout.openDrawer(START)
        }
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

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == fragment.input.key) {
            button.isEnabled = WEB_URL.toRegex().matches(sharedPreferences.getString(key, "")!!)
        }
    }

    fun toggleExpand(@Suppress("UNUSED_PARAMETER") view: View) {
        sheetBehavior.state = when (STATE_COLLAPSED) {
            sheetBehavior.state -> STATE_EXPANDED
            else -> STATE_COLLAPSED
        }
    }

    fun load(@Suppress("UNUSED_PARAMETER") view: View) {
        if (errorbar.isShown) {
            errorbar.dismiss()
        }
        toolbar2.title = getString(R.string.loading)
        progressBar.visibility = View.VISIBLE
        sheetBehavior.state = STATE_COLLAPSED
        picasso.load(fragment.input.text)
            .apply {
                if (fragment.cropCircle.isChecked) circle()
                if (fragment.cropRounded.isChecked) rounded(25.px, 10.px)
                if (fragment.cropSquare.isChecked) square()
                if (fragment.grayscale.isChecked) grayscale()
                if (fragment.mask.isChecked) mask(getDrawable(this@DemoActivity,
                    R.drawable.mask
                )!!)
                if (fragment.overlay.isChecked) overlay(Color.RED)
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
                    progressBar.visibility = View.GONE
                }
                onError {
                    toolbar2.title = getString(R.string.error)
                    progressBar.visibility = View.GONE
                }
            }
    }

    private companion object {
        infix fun Toolbar.assign(@ColorInt color: Int) {
            setBackgroundColor(color)
            subtitle = "#%06X".format(0xFFFFFF and color)

            if ((Color.red(color) + Color.green(color) + Color.blue(color)) / 3 < 127.5) {
                setTitleTextAppearance(context,
                    R.style.TextAppearance_AppCompat_Small
                )
                setSubtitleTextAppearance(context,
                    R.style.TextAppearance_AppCompat_Subhead
                )
            } else {
                setTitleTextAppearance(context,
                    R.style.TextAppearance_AppCompat_Small_Inverse
                )
                setSubtitleTextAppearance(context,
                    R.style.TextAppearance_AppCompat_Subhead_Inverse
                )
            }
        }
    }
}