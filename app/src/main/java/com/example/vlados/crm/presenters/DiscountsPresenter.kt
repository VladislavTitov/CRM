package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers


@InjectViewState
class DiscountsPresenter(val navigator: Navigator?) : MvpPresenter<ItemInterface<Discount>>() {
    
    var discounts: List<Discount> = mutableListOf<Discount>()
    
    fun onItemsReady() {
        ApiMethods.get.getAllDiscounts().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    discounts = it
                    viewState.setItems(it)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }
    
    fun addItem(discount: Discount) {
        discounts += discount
        viewState.setItems(discounts)
    }
    
    fun updateItem(discount: Discount) {
        val position = discounts.indexOfFirst { it -> it.id == discount.id }
        val temp = ArrayList<Discount>(discounts)
        temp.removeAt(position)
        temp.add(position, discount)
        viewState.setItems(temp)
    }
    
}

