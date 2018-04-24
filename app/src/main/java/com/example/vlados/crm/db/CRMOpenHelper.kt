package com.example.vlados.crm.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.vlados.crm.db.contracts.UserContract

const val DATABASE_NAME = "crm.db"
const val VERSION = 1

class CRMOpenHelper(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        db?.let { UserContract.createTable(it) }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}