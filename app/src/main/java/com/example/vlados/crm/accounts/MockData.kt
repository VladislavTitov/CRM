package com.example.vlados.crm.accounts

import com.example.vlados.crm.accounts.data.Account
import com.example.vlados.crm.sales.data.Sale
import java.util.*

fun getAccountListMockData(): List<Account> {
    val n = 20
    val result = mutableListOf<Account>()
    for (i in 0..n) {
        val account = Account()
        result.add(account)
    }
    return result
}

fun getSaleListMockData(): List<Sale> {
    val n = 20
    val result = mutableListOf<Sale>()
    for (i in 0..n) {
        var sale: Sale
        if (i % 3 == 0)
            sale = Sale(approved = true)
        else sale = Sale()
        result.add(sale)
    }
    return result
}

val mockCompanies = arrayOf("Auchan", "H&M", "Zara", "RIVE GAUCHE", "Levi's")

fun getRandomCompany(): String {
    return mockCompanies[Random().nextInt(mockCompanies.size)]
}

val mockJobs = arrayOf("Кассир", "Руководитель", "Уборщик", "Менеджер")

fun getRandomJob(): String {
    return mockJobs[Random().nextInt(mockJobs.size)]
}