package com.example.vlados.crm.sigin

import android.content.Context
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.ADMIN
import com.example.vlados.crm.MANAGER
import com.example.vlados.crm.network.ApiMethods
import com.example.vlados.crm.network.login.LoginRequest
import com.example.vlados.crm.utils.saveCurrentUser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

/**
 * Created by vlados on 26.03.18.
 */
@InjectViewState
class LoginPresenter(val context: Context) : MvpPresenter<LoginInterface>(){

    var subscription: Disposable? = null

    fun signIn(login : String, password : String) {
        viewState.showProgress(true)
        subscription = ApiMethods.post.login(LoginRequest(login, password))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.role != null) {
                        context.saveCurrentUser(it)
                        viewState.goToMainActivity(it.role!!)
                    } else {
                        viewState.showProgress(true)
                        viewState.showMessage("Пользователь не валиден!")
                    }
                }, {
                    it.printStackTrace()
                    viewState.showProgress(true)
                    viewState.showMessage("Error!")
                })
    }

    override fun onDestroy() {
        subscription?.dispose()
    }
}