package com.example.vlados.crm.common

import com.arellomobile.mvp.MvpView

interface ItemInterface<T> : MvpView {

    fun setItems(items: List<T>)
    
}