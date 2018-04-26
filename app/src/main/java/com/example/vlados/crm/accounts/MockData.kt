package com.example.vlados.crm.accounts

import com.example.vlados.crm.accounts.data.Account

fun getAccountListMockData(): List<Account> {
    val n = 20
    val result = mutableListOf<Account>()
    for (i in 0..n) {
        val account = Account()
        result.add(account)
    }
    return result
}