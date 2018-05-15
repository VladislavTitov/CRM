package com.example.vlados.crm.presenters.edit.good

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class GoodEditPresenter(val isNew: Boolean) : MvpPresenter<GoodEditInterface>() {

    fun onSave(good: Good) {
        viewState.showLoading(true)
        if (isNew) {
            ApiMethods.post.createGood(good)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.showLoading(false)
                        if (it) {
                            viewState.notifyObserver()
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
            if (good.id == null) {
                viewState.showMessage("Good id cannot be null!")
                viewState.dismiss()
                return
            }
            ApiMethods.patch.patchGood(good.id, good)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.showLoading(false)
                        if (it) {
                            viewState.notifyObserver()
                            viewState.dismiss()
                        } else {
                            viewState.showMessage("New good is not updated!")
                        }
                    }, {
                        it.printStackTrace()
                        viewState.showLoading(false)
                        viewState.showMessage("Error!")
                    })
        }
    }

    fun onSave(id: Long? = null, name: String, price: Int, kind: String, sizes: String? = null) {
        onSave(Good(id, name, price, kind, sizes))
    }

}