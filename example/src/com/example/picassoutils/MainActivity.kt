package com.example.picassoutils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.squareup.picasso.Picasso
import com.squareup.picasso.getCache
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

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
            Picasso.with(this).getCache().clear()
            toast("Cache cleared")
        }
    }
}