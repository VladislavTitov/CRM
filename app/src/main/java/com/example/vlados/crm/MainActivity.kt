package com.example.vlados.crm

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle

private const val ROLE_KEY = "role_key"

fun Context.MainActivityIntent(role: String): Intent {
    return Intent(this, MainActivity::class.java).apply {
        putExtra(ROLE_KEY, role)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val role = intent.extras.getString(ROLE_KEY, CASHIER)
        when (role) {
            NET_ADMIN -> setContentView(R.layout.activity_main_net)
            SHOP_ADMIN -> setContentView(R.layout.activity_main_shop)
            CASHIER -> setContentView(R.layout.activity_main_cashier)
        }

    }
}
