package com.example.pikasso

import android.content.ClipDescription.MIMETYPE_TEXT_PLAIN
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import android.widget.ImageView
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.placeholders.toHorizontalProgressTarget
import com.hendraanggrian.pikasso.placeholders.toProgressTarget
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.activity_placeholders.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.dip
import org.jetbrains.anko.sdk25.coroutines.textChangedListener

class PlaceholdersActivity : AppCompatActivity() {

    private var clearItem: MenuItem? = null
    private var targetConverter: (ImageView) -> Target = { it.toProgressTarget() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(SOFT_INPUT_STATE_HIDDEN)
        setContentView(R.layout.activity_placeholders)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null
        input.textChangedListener {
            onTextChanged { s, _, _, _ -> clearItem?.isVisible = !s.isNullOrBlank() }
        }
        contentView!!.errorbar("Load image from url with placeholder target") {
            setBackground(R.drawable.errorbar_bg_cloud)
            setContentMarginLeft(dip(16))
            setContentMarginRight(dip(16))
            setAction("Dismiss")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_target, menu)
        clearItem = menu.findItem(R.id.clearItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.clearItem -> input.setText("")
            R.id.pasteItem -> {
                val manager = clipboardManager
                if (manager.hasPrimaryClip() && manager.primaryClipDescription.hasMimeType(MIMETYPE_TEXT_PLAIN)) {
                    input.setText(manager.primaryClip.getItemAt(0).text)
                }
            }
            R.id.sendItem -> when {
                input.text.isNullOrBlank() -> snackbar(contentView!!, "Needs url.")
                else -> {
                    inputMethodManager.hideSoftInputFromWindow(contentView!!.windowToken, HIDE_IMPLICIT_ONLY)
                    picasso.load(input.text.toString()).into(targetConverter(image))
                }
            }
            R.id.progressItem -> {
                item.isChecked = !item.isChecked
                targetConverter = { it.toProgressTarget() }
            }
            R.id.horizontalItem -> {
                item.isChecked = !item.isChecked
                targetConverter = { it.toHorizontalProgressTarget() }
            }
        }
        return true
    }
}