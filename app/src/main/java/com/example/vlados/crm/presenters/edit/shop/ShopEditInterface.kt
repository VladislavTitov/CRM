package com.example.vlados.crm.presenters.edit.shop

import com.arellomobile.mvp.MvpView
import com.example.vlados.crm.db.models.User

interface ShopEditInterface : MvpView {

    fun setSpinnerItems(users: List<User>)

    fun showLoading(isShown: Boolean)

    fun showMessage(text: String)

    fun dismiss()

    fun notifyObserver()

}