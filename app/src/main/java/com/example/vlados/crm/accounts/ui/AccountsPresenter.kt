package com.example.vlados.crm.accounts.ui

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.accounts.data.Account
import com.example.vlados.crm.getAccountListMockData
import com.example.vlados.crm.interfaces.ItemInterface

@InjectViewState
class AccountsPresenter : MvpPresenter<ItemInterface<Account>>() {

    fun onReady() {
        viewState.setItems(getAccountListMockData())
    }


    fun onItemClick(item: Account): Boolean {
        return false
    }
}