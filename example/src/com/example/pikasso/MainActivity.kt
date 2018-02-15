package com.example.pikasso

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun transformationsExample(view: View) = startActivity(Intent(this, TransformationActivity::class.java))

    fun targetsExample(view: View) = startActivity(Intent(this, TargetActivity::class.java))
}