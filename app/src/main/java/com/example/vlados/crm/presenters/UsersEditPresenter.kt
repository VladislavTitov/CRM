package com.example.vlados.crm.presenters

import android.support.v4.app.Fragment
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Shop
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.network.ApiMethods
import com.example.vlados.crm.ui.fragment.UserFragment
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class UsersEditPresenter(val navigator: Navigator?,
                         val parentFragment: Fragment) : MvpPresenter<UserEditInterface>() {

    fun onItemsReady(dialogView: View) {
        ApiMethods.get.getAllShops().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.setShops(it, dialogView)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })

    }

    fun update(user: User?) {
        ApiMethods.patch.patchUser(user?.id, user).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (parentFragment is UserFragment)
                        parentFragment.presenter.updateItem(it)
                    navigator?.showSnack("Updated!")
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }

    fun save(user: User?) {
        ApiMethods.post.postUser(user).observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (parentFragment is UserFragment)
                        parentFragment.presenter.addItem(it)
                    navigator?.showSnack("Saved!")
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }

  
}
interface UserEditInterface : MvpView {
    fun setShops(items: List<Shop>, dialogView: View)
}