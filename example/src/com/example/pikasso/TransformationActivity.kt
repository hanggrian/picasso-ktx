package com.example.pikasso

import android.graphics.Color.RED
import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v4.content.ContextCompat.getDrawable
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.CheckBox
import com.hendraanggrian.pikasso.circle
import com.hendraanggrian.pikasso.grayscale
import com.hendraanggrian.pikasso.mask
import com.hendraanggrian.pikasso.overlay
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.rounded
import com.hendraanggrian.pikasso.square
import com.hendraanggrian.pikasso.toProgressTarget
import kotlinx.android.synthetic.main.activity_transformation.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.dip
import org.jetbrains.anko.forEachChild

class TransformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null
        checkBoxes.forEachChild {
            (it as CheckBox).setOnCheckedChangeListener { _, _ ->
                picasso.load(R.drawable.bg_test)
                    .apply {
                        if (cropSquare.isChecked) square()
                        if (cropCircle.isChecked) circle()
                        if (cropRounded.isChecked) rounded(dip(25), dip(10))
                        if (colorOverlay.isChecked) overlay(RED)
                        if (colorGrayscale.isChecked) grayscale()
                        if (mask.isChecked) mask(getDrawable(this@TransformationActivity, R.drawable.mask)!!)
                    }
                    .into(imageView.toProgressTarget())
            }
        }
        contentView!!.errorbar("Select transformations from selection below") {
            setBackground(R.drawable.errorbar_bg_cloud)
            setContentMarginLeft(dip(16))
            setContentMarginRight(dip(16))
            setAction("Dismiss")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}