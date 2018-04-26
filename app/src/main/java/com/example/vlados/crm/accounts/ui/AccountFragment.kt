package com.example.vlados.crm.accounts.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.accounts.OnItemClickListener
import com.example.vlados.crm.accounts.data.Account
import com.example.vlados.crm.accounts.getAccountListMockData
import com.example.vlados.crm.common.Navigator
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.item_account.*

fun Context.getAccountFragment(): Fragment {
    val fragment = AccountFragment()
    return fragment
}

class AccountFragment : MvpAppCompatFragment(), OnItemClickListener<Account> {


    override fun onClick(t: Account) {
        context.getAccountEditFragment(t).show(fragmentManager, "");
    }


    lateinit var accountAdapter: AccountAdapter

    public fun onClick() {
        context.getAccountEditFragment().show(fragmentManager, "");
    }

    var navigator: Navigator? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        accountAdapter = AccountAdapter(this, getAccountListMockData())
        accountRecycleView.adapter = accountAdapter
        accountRecycleView.layoutManager = LinearLayoutManager(context)
        navigator?.setFabClickListener { onClick() }
    }


    override fun onDetach() {
        super.onDetach()
        navigator = null
    }


    inner class AccountAdapter(val itemClickListener: OnItemClickListener<Account>,
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
}