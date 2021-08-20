package com.example.picasso

import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getDrawable
import androidx.core.util.PatternsCompat.WEB_URL
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_EXPANDED
import com.google.android.material.bottomsheet.BottomSheetBehavior.from
import com.google.android.material.snackbar.Bannerbar
import com.google.android.material.snackbar.bannerbar
import com.hendraanggrian.auto.prefs.BindPreference
import com.hendraanggrian.auto.prefs.PreferencesSaver
import com.hendraanggrian.auto.prefs.Prefs
import com.hendraanggrian.auto.prefs.android.AndroidPreferences
import com.hendraanggrian.auto.prefs.android.preferences
import com.hendraanggrian.appcompat.picasso.buildPicasso
import com.hendraanggrian.appcompat.picasso.circle
import com.hendraanggrian.appcompat.picasso.grayscale
import com.hendraanggrian.appcompat.picasso.into
import com.hendraanggrian.appcompat.picasso.mask
import com.hendraanggrian.appcompat.picasso.overlay
import com.hendraanggrian.appcompat.picasso.rounded
import com.hendraanggrian.appcompat.picasso.square
import com.squareup.picasso.Cache
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_example.*

class ExampleActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {
    @JvmField @BindPreference var inputUrl: String = ""
    @JvmField @BindPreference var isCropCircle = false
    @JvmField @BindPreference var isCropRounded = false
    @JvmField @BindPreference var isCropSquare = false
    @JvmField @BindPreference var isGrayscale = false
    @JvmField @BindPreference var isMask = false
    @JvmField @BindPreference var isOverlay = false

    private val fragment = ExampleFragment()
    private lateinit var sheetBehavior: BottomSheetBehavior<AppBarLayout>

    private lateinit var picasso: Picasso
    private lateinit var prefs: AndroidPreferences
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
        prefs = preferences
        saver = Prefs.bind(prefs, this)

        picasso = buildPicasso {
            loggingEnabled = BuildConfig.DEBUG
            memoryCache = Cache.NONE
        }

        sheetBehavior = from(appbar)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commitNow()
        onSharedPreferenceChanged(prefs, "inputUrl")

        toolbar2.inflateMenu(R.menu.activity_example)
        pasteItem = toolbar2.menu.findItem(R.id.pasteItem).apply {
            setOnMenuItemClickListener {
                saver = Prefs.bind(prefs, this)
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
        else -> super.onBackPressed()
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
        saver = Prefs.bind(prefs, this)
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
            .into(photo) {
                onSuccess {
                    toolbar2.title = "Success"
                    progress.visibility = View.GONE
                }
                onError {
                    toolbar2.title = "Error"
                    progress.visibility = View.GONE
                }
            }
    }
}