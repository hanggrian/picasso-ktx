package com.example.picasso

import androidx.multidex.MultiDexApplication
import com.hendraanggrian.auto.prefs.PreferencesLogger
import com.hendraanggrian.auto.prefs.Prefs
import com.hendraanggrian.auto.prefs.android.Android

class ExampleApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        Prefs.setLogger(PreferencesLogger.Android)
    }
}
