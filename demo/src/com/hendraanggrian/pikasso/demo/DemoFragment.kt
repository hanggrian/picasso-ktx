package com.hendraanggrian.pikasso.demo

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat

class DemoFragment : PreferenceFragmentCompat() {

    // private lateinit var cropCircle: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_demo)
    }
}