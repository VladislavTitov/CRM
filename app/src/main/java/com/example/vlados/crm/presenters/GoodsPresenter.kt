package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class GoodsPresenter(val navigator: Navigator?) : MvpPresenter<ItemInterface<Good>>() {

    fun onGoodsRVReady() {
        ApiMethods.get.getAllGoods().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setItems(it)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }

    fun onItemClick(item : Good) : Boolean {
        return false
    }

}