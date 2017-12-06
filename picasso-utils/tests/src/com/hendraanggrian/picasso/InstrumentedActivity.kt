package com.hendraanggrian.picasso

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar
import com.hendraanggrian.picasso.test.R
import kota.find

class InstrumentedActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrumented)
        setSupportActionBar(find(R.id.toolbar))
        progressBar = find(R.id.progressBar)
        imageView = find(R.id.imageView)
    }
}