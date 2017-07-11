package com.example.picassocommons

import android.app.Application
import com.squareup.picasso.Picassos

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Picassos.isDebug = BuildConfig.DEBUG
    }
}