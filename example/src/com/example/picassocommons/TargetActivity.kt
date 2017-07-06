package com.example.picassocommons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hendraanggrian.picasso.commons.target.Targets
import com.hendraanggrian.support.utils.view.SoftInputs
import com.hendraanggrian.support.utils.widget.Toasts
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_target.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class TargetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        setContentView(R.layout.activity_target)
        buttonClear.setOnClickListener { editText!!.setText("") }
        buttonGo.setOnClickListener {
            SoftInputs.hideNotAlways(this)
            Picasso.with(this)
                    .load(editText.text.toString())
                    .into(Targets.placeholder(imageView)
                            .callback({ _, _ ->
                                Toasts.showShort(this, "Loaded.")
                            }, {
                                Toasts.showShort(this, "Failed.")
                            }, {
                                Toasts.showShort(this, "Prepare...")
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