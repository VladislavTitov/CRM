package com.example.vlados.crm.presenters.edit.good

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class GoodEditPresenter(val isNew: Boolean) : MvpPresenter<GoodEditInterface>() {

    fun onSave(name: String, price: Int, kind: String, sizes: String? = null) {
        if (isNew) {
            viewState.showLoading(true)
            ApiMethods.post.createGood(name, price, kind)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.showLoading(false)
                        if (it) {
                            viewState.dismiss()
                        } else {
                            viewState.showMessage("New good is not created!")
                        }
                    }, {
                        it.printStackTrace()
                        viewState.showLoading(false)
                        viewState.showMessage("Error!")
                    })
        } else {
            viewState.showMessage("UPdateD!")
        }
    }

}