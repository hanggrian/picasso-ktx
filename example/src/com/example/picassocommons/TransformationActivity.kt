package com.example.picassocommons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.CheckBox
import com.hendraanggrian.kota.content.getColor
import com.hendraanggrian.picasso.commons.target.Targets
import com.hendraanggrian.picasso.commons.transformation.Transformations
import com.squareup.picasso.Transformation
import com.squareup.picasso.getPicasso
import kotlinx.android.synthetic.main.activity_transformation.*
import java.util.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class TransformationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transformation)
        setSupportActionBar(toolbar)
        for (checkBox in Arrays.asList<CheckBox>(
                checkBoxCropSquare,
                checkBoxCropCircle,
                checkBoxCropRounded,
                checkBoxColorOverlay,
                checkBoxGrayscale,
                checkBoxMask)) {
            checkBox.setOnCheckedChangeListener { _, _ ->
                getPicasso().load(R.drawable.bg_test)
                        .transform(ArrayList<Transformation>().apply {
                            if (checkBoxCropSquare.isChecked) {
                                add(Transformations.square())
                            }
                            if (checkBoxCropCircle.isChecked) {
                                add(Transformations.circle())
                            }
                            if (checkBoxCropRounded.isChecked) {
                                add(Transformations.rounded(25, 10, true))
                            }
                            if (checkBoxColorOverlay.isChecked) {
                                add(Transformations.overlay(theme.getColor(R.attr.colorAccent, true), 150))
                            }
                            if (checkBoxGrayscale.isChecked) {
                                add(Transformations.grayscale())
                            }
                            if (checkBoxMask.isChecked) {
                                add(Transformations.mask(this@TransformationActivity, R.drawable.mask))
                            }
                        })
                        .into(Targets.placeholder(imageView))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}