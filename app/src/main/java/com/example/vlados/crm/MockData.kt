package com.example.vlados.crm

import com.example.vlados.crm.db.models.Account
import com.example.vlados.crm.db.models.Sale
import com.example.vlados.crm.db.models.Shop
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

fun getShopListMockData():List<Shop>{
    val result= mutableListOf<Shop>()
    for (i in 0..13){
        result.add(Shop())
    }
    return result
}