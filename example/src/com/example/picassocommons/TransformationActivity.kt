package com.example.picassocommons

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.CheckBox
import com.hendraanggrian.picasso.commons.target.Targets
import com.hendraanggrian.picasso.commons.transformation.Transformations
import com.hendraanggrian.support.utils.content.Themes
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
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
                val transformations = ArrayList<Transformation>()
                if (checkBoxCropSquare.isChecked) {
                    transformations.add(Transformations.square())
                }
                if (checkBoxCropCircle.isChecked) {
                    transformations.add(Transformations.circle())
                }
                if (checkBoxCropRounded.isChecked) {
                    transformations.add(Transformations.rounded(25, 10, true))
                }
                if (checkBoxColorOverlay.isChecked) {
                    transformations.add(Transformations.overlay(Themes.getColor(this, R.attr.colorAccent), 150))
                }
                if (checkBoxGrayscale.isChecked) {
                    transformations.add(Transformations.grayscale())
                }
                if (checkBoxMask.isChecked) {
                    transformations.add(Transformations.mask(this, R.drawable.mask))
                }
                Picasso.with(this)
                        .load(R.drawable.bg_test)
                        .transform(transformations)
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