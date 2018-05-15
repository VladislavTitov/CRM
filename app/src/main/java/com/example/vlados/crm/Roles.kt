package com.example.vlados.crm

import android.content.Context

const val CASHIER = "cashier"

const val MANAGER = "manager"

const val ADMIN = "admin"

val mapToTitleId = mapOf(
        CASHIER to R.string.cashier,
        MANAGER to R.string.manager,
        ADMIN to R.string.admin
)

fun getRole(context: Context, title: String?): String {
    return when (title) {
        context.getString(R.string.manager) -> MANAGER
        context.getString(R.string.admin) -> ADMIN
        else -> CASHIER
    }
}

fun getRoleTitle(context: Context, role: String?): String {
    return when (role) {
        MANAGER -> context.getString(R.string.manager)
        ADMIN -> context.getString(R.string.admin)
        else -> context.getString(R.string.cashier)
    }
}

fun getRoles(context: Context, currentUserRole: String?): List<String> {
    val result = mutableListOf<String>()
    
    result.add(context.getString(mapToTitleId.get(CASHIER)!!))

    if (currentUserRole == ADMIN) {
        result.add(context.getString(mapToTitleId.get(MANAGER)!!))
        result.add(context.getString(mapToTitleId.get(ADMIN)!!))
    }
    return result
}

fun getRoleIndex(role: String?): Int {
    return when (role) {
        MANAGER -> 1
        ADMIN -> 2
        else -> 0
    }
}