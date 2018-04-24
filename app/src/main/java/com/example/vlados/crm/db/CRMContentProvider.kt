package com.example.vlados.crm.db

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.example.vlados.crm.db.contracts.UserContract

class CRMContentProvider : ContentProvider(){

    private lateinit var helper: CRMOpenHelper
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    override fun onCreate(): Boolean {
        if (context == null) return false
        helper = CRMOpenHelper(context)
        baseUri = Uri.parse("content://$CONTENT_AUTHORITY")
        uriMatcher.addURI(CONTENT_AUTHORITY, UserContract.TABLE_NAME, USERS_KEYS_MATCHES)
        return true
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, orderBy: String?): Cursor {
        val table = getType(uri)
        return helper.readableDatabase.query(table, projection, selection, selectionArgs, null, null, orderBy)
    }

    override fun insert(uri: Uri, values: ContentValues): Uri {
        val table = getType(uri)
        helper.writableDatabase.insert(table, null, values)
        return uri
    }

    override fun update(uri: Uri, values: ContentValues, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        val matchCode = uriMatcher.match(uri)
        when (matchCode) {
            USERS_KEYS_MATCHES -> return UserContract.TABLE_NAME
        }
        return null
    }

    companion object {
        const val CONTENT_AUTHORITY = "com.example.vlados.crm"
        const val USERS_KEYS_MATCHES = 101
        var baseUri: Uri? = null
    }
}