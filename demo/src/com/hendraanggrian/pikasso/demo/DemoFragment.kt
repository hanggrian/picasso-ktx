package com.hendraanggrian.pikasso.demo

import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.Preference
import android.support.v7.preference.PreferenceFragmentCompat

class DemoFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    lateinit var inputPreference: EditTextPreference
    lateinit var cropCirclePreference: CheckBoxPreference
    lateinit var cropRoundedPreference: CheckBoxPreference
    lateinit var cropSquarePreference: CheckBoxPreference
    lateinit var grayscalePreference: CheckBoxPreference
    lateinit var maskPreference: CheckBoxPreference
    lateinit var overlayPreference: CheckBoxPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_demo)
        inputPreference = find("inputPreference") {
            summary = text
            onPreferenceChangeListener = this@DemoFragment
        }
        cropCirclePreference = find("cropCirclePreference")
        cropRoundedPreference = find("cropRoundedPreference")
        cropSquarePreference = find("cropSquarePreference")
        grayscalePreference = find("grayscalePreference")
        maskPreference = find("maskPreference")
        overlayPreference = find("overlayPreference")
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        preference.summary = value.toString()
        return true
    }

    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
    private inline fun <T : Preference> find(key: CharSequence): T =
        findPreference(key) as T

    private inline fun <T : Preference> find(key: CharSequence, block: T.() -> Unit): T =
        find<T>(key).apply(block)
}