package com.example.picassocommons

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hendraanggrian.support.utils.widget.Toasts
import com.squareup.picasso.Picassos
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener { startActivity(Intent(this, TransformationActivity::class.java)) }
        button2.setOnClickListener { startActivity(Intent(this, TargetActivity::class.java)) }
        button3.setOnClickListener {
            Picassos.getCache(this).clear()
            Toasts.showShort(this, "Cache cleared")
        }
    }
}