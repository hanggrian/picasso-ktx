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
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.from
import com.google.android.material.snackbar.Bannerbar
import com.google.android.material.snackbar.bannerbar
import com.hendraanggrian.pikasso.buildPicasso
import com.hendraanggrian.pikasso.circle
import com.hendraanggrian.pikasso.grayscale
import com.hendraanggrian.pikasso.mask
import com.hendraanggrian.pikasso.overlay
import com.hendraanggrian.pikasso.palette
import com.hendraanggrian.pikasso.rounded
import com.hendraanggrian.pikasso.square
import com.hendraanggrian.prefy.BindPreference
import com.hendraanggrian.prefy.PreferencesSaver
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.android.AndroidPreferences
import com.hendraanggrian.prefy.android.get
import com.hendraanggrian.prefy.bind
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
    @BindPreference lateinit var inputUrl: String
    @BindPreference @JvmField var isCropCircle = false
    @BindPreference @JvmField var isCropRounded = false
    @BindPreference @JvmField var isCropSquare = false
    @BindPreference @JvmField var isGrayscale = false
    @BindPreference @JvmField var isMask = false
    @BindPreference @JvmField var isOverlay = false

    private val fragment = ExampleFragment()
    private lateinit var sheetBehavior: BottomSheetBehavior<AppBarLayout>

    private lateinit var picasso: Picasso
    private lateinit var drawerToggle: ActionBarDrawerToggle

    private lateinit var preferences: AndroidPreferences
    private lateinit var saver: PreferencesSaver

    lateinit var pasteItem: MenuItem
    lateinit var toggleExpandItem: MenuItem
    lateinit var bannerbar: Bannerbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)
        setSupportActionBar(toolbar)
        supportActionBar!!.run {
            title = null
            setDisplayHomeAsUpEnabled(true)
            setHomeButtonEnabled(true)
        }
        preferences = Prefy[this]
        saver = preferences.bind(this)

        picasso = buildPicasso {
            loggingEnabled = BuildConfig.DEBUG
            memoryCache = Cache.NONE
        }
        drawerToggle = ActionBarDrawerToggle(this, drawer, 0, 0).apply {
            isDrawerIndicatorEnabled = true
        }
        drawer.addDrawerListener(drawerToggle)

        sheetBehavior = from(appbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
        onSharedPreferenceChanged(preferences, "inputUrl")

        toolbar2.inflateMenu(R.menu.activity_example)
        pasteItem = toolbar2.menu.findItem(R.id.pasteItem).apply {
            setOnMenuItemClickListener {
                saver = preferences.bind(this)
                fragment.listView.smoothScrollToPosition(0)
                (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).run {
                    if (hasPrimaryClip() && primaryClipDescription!!
                            .hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
                    ) {
                        val clipboard = primaryClip!!.getItemAt(0).text.toString()
                        inputUrl = clipboard
                        // fragment.onPreferenceChange(fragment.input, clipboard) // trigger
                        saver.save()
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

        bannerbar = appbar.bannerbar("Expand panel below to start loading") {
            "Expand" { sheetBehavior.state = STATE_EXPANDED }
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
        drawer.isDrawerOpen(START) -> drawer.closeDrawer(START)
        else -> super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            drawer.openDrawer(START)
        }
        return false
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerToggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle.onConfigurationChanged(newConfig)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String) {
        if (key == "inputUrl") {
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
        saver = preferences.bind(this)
        if (bannerbar.isShown) bannerbar.dismiss()
        toolbar2.title = "Loading"
        progress.visibility = View.VISIBLE
        sheetBehavior.state = STATE_COLLAPSED
        picasso.load(inputUrl)
            .apply {
                if (isCropCircle) circle()
                if (isCropRounded) rounded(25.px, 10.px)
                if (isCropSquare) square()
                if (isGrayscale) grayscale()
                if (isMask) mask(getDrawable(this@ExampleActivity, R.drawable.mask)!!)
                if (isOverlay) overlay(Color.RED)
            }
            .palette(photo) {
                onSuccess {
                    useVibrant { vibrantToolbar assign it }
                    useLightVibrant { lightVibrantToolbar assign it }
                    useDarkVibrant { darkVibrantToolbar assign it }
                    useMuted { mutedToolbar assign it }
                    useLightMuted { lightMutedToolbar assign it }
                    useDarkMuted { darkMutedToolbar assign it }
                    useDominant { dominantToolbar assign it }
                    toolbar2.title = "Success"
                    progress.visibility = View.GONE
                }
                onError {
                    toolbar2.title = "Error"
                    progress.visibility = View.GONE
                }
            }
    }

    private companion object {
        infix fun Toolbar.assign(@ColorInt color: Int) {
            setBackgroundColor(color)
            subtitle = "#%06X".format(0xFFFFFF and color)

            if ((Color.red(color) + Color.green(color) + Color.blue(color)) / 3 < 127.5) {
                setTitleTextAppearance(
                    context,
                    R.style.TextAppearance_AppCompat_Small
                )
                setSubtitleTextAppearance(
                    context,
                    R.style.TextAppearance_AppCompat_Subhead
                )
            } else {
                setTitleTextAppearance(
                    context,
                    R.style.TextAppearance_AppCompat_Small_Inverse
                )
                setSubtitleTextAppearance(
                    context,
                    R.style.TextAppearance_AppCompat_Subhead_Inverse
                )
            }
        }
    }
}