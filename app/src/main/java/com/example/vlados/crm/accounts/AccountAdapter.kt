package com.example.vlados.crm.accounts

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vlados.crm.R
import com.example.vlados.crm.accounts.data.Account
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_account.*

/**
 * Created by Daria Popova on 25.04.18.
 */
class AccountAdapter(val itemClickListener: OnItemClickListener<Account>,
                     val accouts: List<Account>) : RecyclerView.Adapter<AccountAdapter.AccountHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AccountHolder {
        val view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_account, parent, false)

        return AccountHolder(view)
    }

    override fun getItemCount(): Int {
        return accouts.size
    }

    override fun onBindViewHolder(holder: AccountHolder?, position: Int) {
        holder?.bind(accouts[position])
    }

    inner class AccountHolder(override val containerView: View) :
            RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener {


        fun bind(account: Account) {
            accountInstanceName.text = account.getFullName()
            accountInstanceJob.text = account.job
        }

        override fun onClick(v: View?) {
            itemClickListener.onClick(accouts[adapterPosition])
        }

        init {
            containerView.setOnClickListener(this)
        }

    }

}