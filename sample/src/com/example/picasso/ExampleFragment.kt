package com.example.picasso

import android.os.Bundle
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class ExampleFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_example)
        findPreference<EditTextPreference>("inputUrl")!!.run {
            summary = text
            setOnPreferenceChangeListener { preference, value ->
                preference.summary = value.toString()
                true
            }
        }
    }
}