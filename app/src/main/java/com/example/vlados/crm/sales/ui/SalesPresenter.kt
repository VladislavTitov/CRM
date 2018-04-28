package com.example.vlados.crm.sales.ui

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.getSaleListMockData
import com.example.vlados.crm.interfaces.ItemInterface
import com.example.vlados.crm.sales.data.Sale


@InjectViewState
class SalesPresenter : MvpPresenter<ItemInterface<Sale>>() {


    fun onItemsReady() {
        viewState.setItems(getSaleListMockData())
    }

}