package com.example.vlados.crm.accounts

import com.example.vlados.crm.accounts.data.Account
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

val mockCompanies = arrayOf("Auchan", "H&M", "Zara", "RIVE GAUCHE", "Levi's")

fun getRandomCompany(): String {
    return mockCompanies[Random().nextInt(mockCompanies.size)]
}

val mockJobs = arrayOf("Кассир", "Руководитель", "Уборщик", "Менеджер")

fun getRandomJob(): String {
    return mockJobs[Random().nextInt(mockJobs.size)]
}