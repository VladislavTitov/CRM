package com.example.vlados.crm.db.contracts

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.provider.BaseColumns
import com.example.vlados.crm.db.CRMContentProvider
import com.example.vlados.crm.db.models.User

class UserContract {
    companion object {
        val TABLE_NAME = "users"

        fun createTable(db : SQLiteDatabase) {
            val script = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
                        "${UserContractEntry.ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "${UserContractEntry.USERNAME} TEXT NOT NULL UNIQUE, " +
                        "${UserContractEntry.PASSWORD} TEXT NOT NULL, " +
                        "${UserContractEntry.ROLE} TEXT NOT NULL" +
                    ")"
            db.execSQL(script)
        }

        fun toContentValues(user : User) : ContentValues {
            val values = ContentValues()
            if (user.id!! > 0) {
                values.put(UserContractEntry.ID, user.id)
            }
            values.put(UserContractEntry.USERNAME, user.email)
            values.put(UserContractEntry.PASSWORD, user.password)
            values.put(UserContractEntry.ROLE, user.role)
            return values
        }

        fun fromCursor(cursor: Cursor?) : List<User>? {
            if (cursor == null) {
                return null
            }
            val userList = mutableListOf<User>()
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndex(UserContractEntry.ID))
                val username = cursor.getString(cursor.getColumnIndex(UserContractEntry.USERNAME))
                val password = cursor.getString(cursor.getColumnIndex(UserContractEntry.PASSWORD))
                val role = cursor.getString(cursor.getColumnIndex(UserContractEntry.ROLE))
//                val user = User(id, username, password, role)
//                userList.add(user)
            }
            return userList
        }

        fun getBaseUri() : Uri? {
            return CRMContentProvider.baseUri?.buildUpon()?.appendPath(TABLE_NAME)?.build()
        }

        object UserContractEntry : BaseColumns {
            const val ID = "id"
            const val USERNAME = "username"
            const val PASSWORD = "password"
            const val ROLE = "role"
        }
    }
}