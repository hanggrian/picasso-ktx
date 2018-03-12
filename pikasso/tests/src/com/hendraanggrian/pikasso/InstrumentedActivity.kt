package com.hendraanggrian.pikasso

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar
import com.hendraanggrian.pikasso.test.R

class InstrumentedActivity : AppCompatActivity() {

    lateinit var progressBar: ProgressBar
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_instrumented)
        setSupportActionBar(findViewById(R.id.toolbar))
        progressBar = findViewById(R.id.progressBar)
        imageView = findViewById(R.id.imageView)
    }
}