package com.hendraanggrian.appcompat.picasso

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.hendraanggrian.appcompat.picasso.test.R

class TestActivity : AppCompatActivity() {
    lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        imageView = findViewById(R.id.imageView)
    }
}
