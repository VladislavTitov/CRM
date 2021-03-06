package com.example.vlados.crm.sigin

import com.arellomobile.mvp.MvpView

/**
 * Created by vlados on 26.03.18.
 */
interface LoginInterface : MvpView{

    fun goToMainActivity(role: String)

    fun showMessage(text: String)

    fun showProgress(show: Boolean)

}