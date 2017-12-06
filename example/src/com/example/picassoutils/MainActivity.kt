package com.example.picassoutils

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button1.setOnClickListener { startActivity(Intent(this, TransformationActivity::class.java)) }
        button2.setOnClickListener { startActivity(Intent(this, TargetActivity::class.java)) }
    }
}