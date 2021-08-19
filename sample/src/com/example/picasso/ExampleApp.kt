package com.example.picasso

import androidx.multidex.MultiDexApplication
import com.hendraanggrian.prefs.PreferencesLogger
import com.hendraanggrian.prefs.Prefs
import com.hendraanggrian.prefs.android.Android

class ExampleApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Prefs.setLogger(PreferencesLogger.Android)
    }
}