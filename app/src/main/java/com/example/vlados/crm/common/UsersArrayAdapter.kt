package com.example.vlados.crm.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.vlados.crm.R
import com.example.vlados.crm.db.models.User
import kotlinx.android.synthetic.main.item_spinner_user.view.*

class UsersArrayAdapter(context: Context, private val accounts: List<User>) : ArrayAdapter<User>(context, 0, accounts) {

    fun getPosition(userId: Long?): Int {
        return accounts.indexOfFirst {
            it.id == userId
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_spinner_user, parent, false)
        val acc = accounts[position]
        view.fullname.text = acc.getFullName()
        view.login.text = acc.username
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return getView(position, convertView, parent)
    }
}