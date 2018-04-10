package com.example.vlados.crm.utils

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText


/**
 * Created by vlados on 26.03.18.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}

fun Context.getApplicationName(): String {
    val applicationInfo = this.applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else this.getString(stringId)
}

val Context.defaultProperties: SharedPreferences
    get() = this.getSharedPreferences(this.getApplicationName(), Context.MODE_PRIVATE)