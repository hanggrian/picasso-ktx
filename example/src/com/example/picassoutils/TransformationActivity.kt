package com.example.picassoutils

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.hendraanggrian.picasso.picasso
import com.hendraanggrian.picasso.Targets
import com.hendraanggrian.picasso.Transformations
import com.squareup.picasso.Transformation
import kota.resources.dp
import kota.resources.getColorAttr
import kotlinx.android.synthetic.main.activity_transformation.*
import java.util.*

class TransformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation)
        setSupportActionBar(toolbar)
        listOf(checkBoxCropSquare, checkBoxCropCircle, checkBoxCropRounded, checkBoxColorOverlay, checkBoxGrayscale, checkBoxMask).forEach {
            it.setOnCheckedChangeListener { _, _ ->
                picasso(R.drawable.bg_test)
                        .transform(ArrayList<Transformation>().apply {
                            if (checkBoxCropSquare.isChecked) add(Transformations.square())
                            if (checkBoxCropCircle.isChecked) add(Transformations.circle())
                            if (checkBoxCropRounded.isChecked) add(Transformations.rounded(25.dp, 10.dp))
                            if (checkBoxColorOverlay.isChecked) add(Transformations.overlay(getColorAttr(R.attr.colorAccent)))
                            if (checkBoxGrayscale.isChecked) add(Transformations.grayscale())
                            if (checkBoxMask.isChecked) add(Transformations.mask(this@TransformationActivity, R.drawable.mask))
                        })
                        .into(Targets.progress(imageView))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}