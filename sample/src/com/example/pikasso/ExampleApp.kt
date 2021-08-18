package com.example.pikasso

import android.app.Application
import com.hendraanggrian.prefy.PreferencesLogger
import com.hendraanggrian.prefy.Prefy
import com.hendraanggrian.prefy.android.Android

class ExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Prefy.setLogger(PreferencesLogger.Android)
    }
}