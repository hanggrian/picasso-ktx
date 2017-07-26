package com.hendraanggrian.picasso.commons.test

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class InstrumentedActivity : AppCompatActivity() {

    var progressBar: ProgressBar? = null
    var imageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrumented)
        setSupportActionBar(findViewById(R.id.toolbar))
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
    }
}