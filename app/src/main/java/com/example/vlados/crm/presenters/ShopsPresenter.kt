package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Shop
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by Daria Popova on 04.05.18.
 */
@InjectViewState
class ShopsPresenter(val navigator: Navigator?) : MvpPresenter<ItemInterface<Shop>>() {


    fun onItemsReady() {
        ApiMethods.get.getAllShops().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setItems(it)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })

    }

}