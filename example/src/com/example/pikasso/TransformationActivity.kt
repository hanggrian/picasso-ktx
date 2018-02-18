package com.example.pikasso

import android.graphics.Color.RED
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hendraanggrian.pikasso.circle
import com.hendraanggrian.pikasso.grayscale
import com.hendraanggrian.pikasso.mask
import com.hendraanggrian.pikasso.overlay
import com.hendraanggrian.pikasso.picasso
import com.hendraanggrian.pikasso.rounded
import com.hendraanggrian.pikasso.square
import com.hendraanggrian.pikasso.toHorizontalProgressTarget
import kota.resources.dp
import kotlinx.android.synthetic.main.activity_transformation.*

class TransformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation)
        setSupportActionBar(toolbar)
        listOf(
            checkBoxCropSquare, checkBoxCropCircle, checkBoxCropRounded,
            checkBoxColorOverlay, checkBoxGrayscale, checkBoxMask
        ).forEach {
            it.setOnCheckedChangeListener { _, _ ->
                picasso(R.drawable.bg_test)
                    .apply {
                        if (checkBoxCropSquare.isChecked) square()
                        if (checkBoxCropCircle.isChecked) circle()
                        if (checkBoxCropRounded.isChecked) rounded(25.dp, 10.dp)
                        if (checkBoxColorOverlay.isChecked) overlay(RED)
                        if (checkBoxGrayscale.isChecked) grayscale()
                        if (checkBoxMask.isChecked) mask(this@TransformationActivity, R.drawable.mask)
                    }
                    .into(imageView.toHorizontalProgressTarget())
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}