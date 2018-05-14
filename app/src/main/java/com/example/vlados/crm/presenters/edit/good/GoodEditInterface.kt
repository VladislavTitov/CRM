package com.example.vlados.crm.presenters.edit.good

import com.arellomobile.mvp.MvpView

interface GoodEditInterface : MvpView {

    fun showLoading(isShown: Boolean)

    fun dismiss()

    fun showMessage(message: String)

}