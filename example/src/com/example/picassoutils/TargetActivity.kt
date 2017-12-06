package com.example.picassoutils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager.HIDE_IMPLICIT_ONLY
import com.hendraanggrian.picasso.picasso
import com.hendraanggrian.picasso.Targets
import kota.inputMethodManager
import kota.toast
import kotlinx.android.synthetic.main.activity_target.*

class TargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_target)
        setSupportActionBar(toolbar)
        buttonClear.setOnClickListener { editText.setText("") }
        buttonGo.setOnClickListener {
            inputMethodManager!!.hideSoftInputFromWindow(it.windowToken, HIDE_IMPLICIT_ONLY)
            picasso(editText.text.toString())
                    .into(Targets.progress(imageView)
                            .callback({ _, _ ->
                                toast("Loaded.")
                            }, {
                                toast("Failed.")
                            }, {
                                toast("Prepare...")
                            }))
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}