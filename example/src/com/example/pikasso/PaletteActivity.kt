package com.example.pikasso

import android.os.Bundle
import android.support.design.widget.errorbar
import android.support.v7.app.AppCompatActivity
import com.hendraanggrian.pikasso.palette.palette
import com.hendraanggrian.pikasso.picasso
import kotlinx.android.synthetic.main.activity_palette.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.dip

class PaletteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = null
        contentView!!.errorbar("Cool, right?") {
            setBackground(R.drawable.errorbar_bg_cloud)
            setContentMarginLeft(dip(16))
            setContentMarginRight(dip(16))
            setAction("Dismiss")
        }

        picasso.load(R.drawable.bg_test).palette(imageView) {
            vibrant {
                titleView.setTextColor(it)
            }
        }
    }
}