package com.example.vlados.crm.db.models

import com.example.vlados.crm.CASHIER

data class User(var id : Int, val username : String, val password : String, val role : String = CASHIER)