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

    fun transformations(v: View) = startActivity(Intent(this, TransformationsActivity::class.java))

    fun targets(v: View) = startActivity(Intent(this, PlaceholdersActivity::class.java))
}