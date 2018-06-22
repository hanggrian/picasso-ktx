package com.hendraanggrian.pikasso.demo

import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.ListPreference
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
    lateinit var placeholdersPreference: ListPreference

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
        placeholdersPreference = find("placeholdersPreference") {
            // set default to none
            if (value == null) {
                setValueIndex(0)
            }
            summary = getPlaceholderEntry(entries, value)
            onPreferenceChangeListener = this@DemoFragment
        }
    }

    override fun onPreferenceChange(preference: Preference, value: Any): Boolean {
        preference.summary = when (preference) {
            placeholdersPreference ->
                getPlaceholderEntry(placeholdersPreference.entries, value.toString())
            else -> value.toString()
        }
        return true
    }

    private fun getPlaceholderEntry(
        entries: Array<CharSequence>,
        value: CharSequence
    ): CharSequence {
        val values = context!!.resources.getStringArray(R.array.placeholders_values)
        return entries[values.indexOf(value)]
    }

    @Suppress("NOTHING_TO_INLINE", "UNCHECKED_CAST")
    private inline fun <T : Preference> find(key: CharSequence): T =
        findPreference(key) as T

    private inline fun <T : Preference> find(key: CharSequence, block: T.() -> Unit): T =
        find<T>(key).apply(block)
}