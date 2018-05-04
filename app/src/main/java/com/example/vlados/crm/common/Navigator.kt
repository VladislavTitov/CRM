package com.example.vlados.crm.common

import android.view.View

interface Navigator {
    fun setFabClickListener(l: (view: View) -> Unit)
    fun showSnack(text: String)

    interface InnerNavigator{
        fun changeFab()
    }
}