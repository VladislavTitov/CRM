package com.example.vlados.crm.common

import android.view.View

interface Navigator {

    fun setFabClickListener(l: (view: View) -> Unit)
    fun showSnack(text: String)
    fun hibeFab()

    interface InnerNavigator {
        fun changeFab()
    }
}