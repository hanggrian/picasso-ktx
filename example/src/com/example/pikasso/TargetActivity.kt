package com.example.pikasso

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.toProgressBarTarget
import kota.inputMethodManager
import kota.toast
import kotlinx.android.synthetic.main.activity_target.*

class TargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
        setSupportActionBar(toolbar)
    }

    fun clear(view: View) = editText.setText("")

    fun go(view: View) {
        inputMethodManager!!.hideSoftInputFromWindow(view.windowToken, HIDE_IMPLICIT_ONLY)
        picasso(editText.text.toString())
            .into(imageView.toProgressBarTarget()
                .onLoaded { _, _ -> toast("Loaded.") }
                .onFailed { toast("Failed.") }
                .onPrepare { toast("Prepare...") })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}