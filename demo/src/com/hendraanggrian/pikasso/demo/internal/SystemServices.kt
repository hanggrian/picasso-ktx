package com.hendraanggrian.pikasso.demo.internal

import android.content.ClipboardManager
import android.content.Context
import android.view.inputmethod.InputMethodManager

inline val Context.clipboardManager: ClipboardManager
    get() = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

inline val Context.inputMethodManager: InputMethodManager
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager