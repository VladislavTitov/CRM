package com.example.vlados.crm.sigin

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.ADMIN
import com.example.vlados.crm.MANAGER
import io.reactivex.disposables.Disposable

/**
 * Created by vlados on 26.03.18.
 */
@InjectViewState
class LoginPresenter : MvpPresenter<LoginInterface>(){

    var subscription: Disposable? = null

    fun signIn(login : String, password : String) {
        /*subscription = ApiMethods.post.login()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()*/ //TODO implement retrieving and saving credentials
        viewState.goToMainActivity(ADMIN)
    }

    override fun onDestroy() {
        subscription?.dispose()
    }
}