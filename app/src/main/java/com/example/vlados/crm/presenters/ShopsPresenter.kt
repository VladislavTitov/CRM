package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.db.models.Shop
import com.example.vlados.crm.getShopListMockData

/**
 * Created by Daria Popova on 04.05.18.
 */
@InjectViewState
class ShopsPresenter : MvpPresenter<ItemInterface<Shop>>() {


    fun onItemsReady() {
        viewState.setItems(getShopListMockData())
    }

}