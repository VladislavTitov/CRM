package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.User
import com.example.vlados.crm.network.ApiMethods
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class UsersPresenter(val navigator: Navigator?) : MvpPresenter<ItemInterface<User>>() {
    
    var users: List<User> = mutableListOf()
    
    fun onReady() {
        ApiMethods.get.getAllUsers().observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    users = it
                    viewState.setItems(it)
                }, {
                    it.printStackTrace()
                    navigator?.showSnack("Error!")
                })
    }
    
    fun addItem(user: User) {
        users += user
        viewState.setItems(users)
    }
    
    fun updateItem(user: User) {
        val position = users.indexOfFirst { it -> it.id == user.id }
        val temp = ArrayList<User>(users)
        temp.removeAt(position)
        temp.add(position, user)
        viewState.setItems(temp)
    }
    
    
}