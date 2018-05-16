package com.example.vlados.crm.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.example.vlados.crm.db.models.User
import com.google.gson.Gson


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

const val USER_KEY = "user_key"

fun Context.getCurrentUser(): User? {
    val sp = PreferenceManager.getDefaultSharedPreferences(this)
    val jsonUser = sp.getString(USER_KEY, null)
    if (jsonUser == null)
        return null
    return Gson().fromJson(jsonUser, User::class.java)
}

fun Context.saveCurrentUser(user: User) {
    val sp = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = sp.edit()
    editor.putString(USER_KEY, Gson().toJson(user))
    editor.apply()
}

fun Context.deleteCurrentUser() {
    val sp = PreferenceManager.getDefaultSharedPreferences(this)
    val editor = sp.edit()
    editor.putString(USER_KEY, null)
    editor.apply()
}

fun Context.getApplicationName(): String {
    val applicationInfo = this.applicationInfo
    val stringId = applicationInfo.labelRes
    return if (stringId == 0) applicationInfo.nonLocalizedLabel.toString() else this.getString(stringId)
}

val Context.defaultProperties: SharedPreferences
    get() = this.getSharedPreferences(this.getApplicationName(), Context.MODE_PRIVATE)