package com.example.vlados.crm.presenters.edit.shop

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.MANAGER
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.network.ApiMethods
import com.example.vlados.crm.network.shop.ShopCreateUpdateRequest
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ShopEditPresenter(val isNew: Boolean, val navigator: Navigator) : MvpPresenter<ShopEditInterface>() {

    fun onSpinnerReady() {
        ApiMethods.get.getRoledUsers(MANAGER)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setSpinnerItems(it)
                }, {
                    it.printStackTrace()
                    viewState.showMessage("Error during managers loading!")
                })
    }

    fun onSave(id: Long? = null, name: String, address: String, adminId: Long) {
        onSave(ShopCreateUpdateRequest(id, name, address, adminId))
    }

    fun onSave(shop: ShopCreateUpdateRequest) {
        viewState.showLoading(true)
        if (isNew) {
            ApiMethods.post.createShop(shop)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        if (it) {
                            viewState.notifyObserver()
                            viewState.dismiss()
                        } else {
                            viewState.showMessage("Shop is not created!")
                        }
                    }, {
                        it.printStackTrace()
                        viewState.showLoading(false)
                        viewState.showMessage("Error during creating shop!")
                    })
        } else {
            if (shop.id == null) {
                viewState.showMessage("Shop id cannot be null!")
                viewState.dismiss()
                return
            }
            ApiMethods.patch.patchShop(shop.id, shop)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.showLoading(false)
                        if (it) {
                            viewState.notifyObserver()
                            viewState.dismiss()
                        } else {
                            viewState.showMessage("Shop is not updated!")
                        }
                    }, {
                        it.printStackTrace()
                        viewState.showLoading(false)
                        viewState.showMessage("Error during updating shop!")
                    })
        }
    }

}