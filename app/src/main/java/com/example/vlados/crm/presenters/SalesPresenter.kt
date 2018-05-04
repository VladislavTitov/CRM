package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.db.models.Sale
import com.example.vlados.crm.getSaleListMockData


@InjectViewState
class SalesPresenter : MvpPresenter<ItemInterface<Sale>>() {


    fun onItemsReady() {
        viewState.setItems(getSaleListMockData())
    }

}