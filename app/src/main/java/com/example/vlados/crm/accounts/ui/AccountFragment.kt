package com.example.vlados.crm.accounts.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.accounts.AccountAdapter
import com.example.vlados.crm.accounts.OnItemClickListener
import com.example.vlados.crm.accounts.data.Account
import com.example.vlados.crm.accounts.getAccountListMockData
import com.example.vlados.crm.common.Navigator
import kotlinx.android.synthetic.main.fragment_account.*

fun Context.Account(): Fragment {
    val fragment = AccountFragment()
    return fragment
}

class AccountFragment : MvpAppCompatFragment(), OnItemClickListener<Account> {


    override fun onClick(t: Account) {
        context.AccountEdit(t).show(fragmentManager, "");
    }


    lateinit var accountAdapter: AccountAdapter

    public fun onClick() {
        context.AccountEdit().show(fragmentManager, "");
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
        //accountFab.setOnClickListener { onClick() }
    }


    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
}