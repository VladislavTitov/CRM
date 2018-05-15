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

    var goods: MutableList<Good>? = null

    fun onGoodsRVReady() {
        ApiMethods.get.getAllGoods().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    goods = it
                    viewState.setItems(it)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }

    fun onItemClick(item : Good) : Boolean {
        return false
    }

    fun deleteGood(goodId: Long) {
        ApiMethods.delete.deleteGood(goodId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    /*if (removeGood(it)) {
                        viewState.setItems(goods ?: mutableListOf())
                    }*/
                    onGoodsRVReady()
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }

    private fun removeGood(good: Good): Boolean {
        if (goods == null) {
            return false
        }
        var deletingGood: Good? = null
        for (item in goods!!) {
            if (item.id == good.id) {
                deletingGood = item
                break
            }
        }
        if (deletingGood == null) return false
        return goods?.remove(deletingGood) ?: false
    }

}