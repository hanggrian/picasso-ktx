package com.example.pikasso

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class DemoFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    lateinit var input: EditTextPreference
    lateinit var cropCircle: CheckBoxPreference
    lateinit var cropRounded: CheckBoxPreference
    lateinit var cropSquare: CheckBoxPreference
    lateinit var grayscale: CheckBoxPreference
    lateinit var mask: CheckBoxPreference
    lateinit var overlay: CheckBoxPreference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.fragment_demo)
        input = find(PREFERENCE_INPUT_URL) {
            summary = text
            onPreferenceChangeListener = this@DemoFragment
        }
        cropCircle = find(PREFERENCE_CROP_CIRCLE)
        cropRounded = find(PREFERENCE_CROP_ROUNDED)
        cropSquare = find(PREFERENCE_CROP_SQUARE)
        grayscale = find(PREFERENCE_GRAYSCALE)
        mask = find(PREFERENCE_MASK)
        overlay = find(PREFERENCE_OVERLAY)
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