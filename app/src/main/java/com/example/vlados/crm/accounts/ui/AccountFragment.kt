package com.example.vlados.crm.accounts.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.accounts.data.Account
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.interfaces.ItemInterface
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.item_account.*

fun Context.getAccountFragment(): Fragment {
    val fragment = AccountFragment()
    return fragment
}

class AccountFragment : MvpAppCompatFragment(), ItemInterface<Account> {

    lateinit var accountAdapter: AccountAdapter
    var navigator: Navigator? = null
    @InjectPresenter
    lateinit var presenter: AccountsPresenter

    @ProvidePresenter
    fun providePresenter(): AccountsPresenter {
        return AccountsPresenter()
    }


    public fun onFabClick() {
        context.getAccountEditFragment().show(fragmentManager, "");
    }

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
        accountAdapter = AccountAdapter { presenter.onItemClick(it) }
        accountRecycleView.adapter = accountAdapter
        accountRecycleView.layoutManager = LinearLayoutManager(context)
        presenter.onReady()
    }

    override fun onResume() {
        super.onResume()
        setFabListener()
    }

    private fun setFabListener() {
        navigator?.setFabClickListener { onFabClick() }
    }


    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    override fun setItems(items: List<Account>) {
        accountAdapter.setItems(items)
    }

    inner class AccountAdapter(private val onItemClickLister: (item: Account) -> Boolean) : RecyclerView.Adapter<AccountAdapter.AccountHolder>() {

        var accounts = listOf<Account>()

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AccountHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_account, parent, false)

            return AccountHolder(view,
                    onItemClickLister = onItemClickLister,
                    onItemChange = { position -> notifyItemChanged(position) })
        }

        override fun getItemCount(): Int {
            return accounts.size
        }

        override fun onBindViewHolder(holder: AccountHolder?, position: Int) {
            holder?.bind(accounts[position], position)
        }

        fun setItems(new: List<Account>) {

            val diffUtilsCallback = GenericDiffUtilsCallback<Account>(accounts, new,
                    { oldPosition, newPosition ->
                        accounts[oldPosition].id == new[newPosition].id
                    },
                    { oldPosition, newPosition ->
                        val oldItem = accounts[oldPosition]
                        val newItem = new[newPosition]
                        oldItem.name == newItem.name &&
                                oldItem.address == newItem.address &&
                                oldItem.job == newItem.job &&
                                oldItem.login == newItem.login &&
                                oldItem.password == newItem.password &&
                                oldItem.company == newItem.company &&
                                oldItem.surname == newItem.surname &&
                                oldItem.store == newItem.store
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            accounts = new
            result.dispatchUpdatesTo(this)
        }

        inner class AccountHolder(override val containerView: View,
                                  private val onItemClickLister: (item: Account) -> Boolean,
                                  private val onItemChange: (Int) -> Unit) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {


            fun bind(account: Account, position: Int) {
                accountInstanceName.text = account.getFullName()
                accountInstanceJob.text = account.job

                containerView.setOnClickListener {
//                    if (onItemClickLister(account))
//                        onItemChange(position)
                    onClick(account)
                }
            }

            private fun onClick(account: Account){
                context.getAccountEditFragment(account).show(fragmentManager, "")
            }

        }

    }
}