package com.example.vlados.crm.presenters

import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.network.ApiMethods
import com.example.vlados.crm.ui.holders.DiscountsHolderFragment
import io.reactivex.android.schedulers.AndroidSchedulers


@InjectViewState
class DiscountEditPresenter(val navigator: Navigator?,
                            val parentFragment: Fragment) : MvpPresenter<DiscountEditInterface>() {
    
    
    fun onItemsReady(dialogView: View) {
        ApiMethods.get.getAllGoods().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setGoods(it, dialogView)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
        
    }
    
    fun update(discount: Discount?) {
        ApiMethods.patch.patchDiscount(discount?.id, discount).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (parentFragment is DiscountsHolderFragment)
                        parentFragment.presenter.updateItem(it)
                    navigator?.showSnack("Updated!")
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }
    
    fun save(discount: Discount?) {
        ApiMethods.post.postDiscount(discount).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (parentFragment is DiscountsHolderFragment)
                        parentFragment.presenter.addItem(it)
                    navigator?.showSnack("Saved!")
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }
    
}

interface DiscountEditInterface : MvpView {
    fun setGoods(items: List<Good>, dialogView: View)
}