package com.example.vlados.crm.common

import android.app.AlertDialog
import android.app.Dialog
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import com.arellomobile.mvp.MvpAppCompatDialogFragment


abstract class EditMvpAppCompatDialogFragment : MvpAppCompatDialogFragment(){

    abstract fun checkCorrectness(view: View): Boolean


    fun isEmpty(edit: EditText): Boolean {
        return TextUtils.isEmpty(edit.text.toString())
    }

    fun setError(edit: EditText?, message: String) {
        edit?.setError(message)
    }

    abstract fun save(view: View)

    fun changePosButton(view: View) {
        val d = dialog
        if (d is AlertDialog) {
            val posButton = d.getButton(Dialog.BUTTON_POSITIVE)
            posButton.setOnClickListener { save(view) }
        }
    }

    fun getFromEdit(edit: EditText): String {
        return edit.text.toString()
    }
}