package com.example.vlados.crm.interfaces

import com.arellomobile.mvp.MvpView

interface ItemInterface<T> : MvpView {

    fun setItems(items: List<T>)
}