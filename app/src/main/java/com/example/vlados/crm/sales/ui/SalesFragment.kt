package com.example.vlados.crm.sales.ui

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
import com.example.vlados.crm.accounts.getSaleListMockData
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.sales.data.Sale
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_sales.*
import kotlinx.android.synthetic.main.item_sale.*

/**
 * Created by Daria Popova on 26.04.18.
 */


fun Context.getSalesFragment(approved: Boolean = false): Fragment {
    val fragment = SalesFragment()
    val args = Bundle()
    args.putBoolean(SalesFragment.APPROVED_KEY, approved)
    fragment.arguments = args
    return fragment
}

class SalesFragment : MvpAppCompatFragment(), OnItemClickListener<Sale> {

    override fun onClick(t: Sale) {

    }

    companion object {
        const val APPROVED_KEY = "approved"
    }

    lateinit var salesAdapter: SalesAdapter

    public fun onClick() {
        context.getSaleEditFragment().show(fragmentManager, "");
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
        val view = inflater.inflate(R.layout.fragment_sales, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onResume() {
        super.onResume()
        setFabClickListener()
    }

    private fun setFabClickListener() {
        navigator?.setFabClickListener { onClick() }
    }


    fun init() {
        salesAdapter = SalesAdapter(this, getSaleListMockData())
        rvSales.adapter = salesAdapter
        rvSales.layoutManager = LinearLayoutManager(context)

    }


    override fun onDetach() {
        super.onDetach()
        navigator = null
    }



    inner class SalesAdapter(val itemClickListener: OnItemClickListener<Sale>,
                             val sales: List<Sale>) : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {




        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SalesHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_sale, parent, false)

            return SalesHolder(view)
        }

        override fun getItemCount(): Int {
            return sales.size
        }

        override fun onBindViewHolder(holder: SalesHolder?, position: Int) {
            holder?.bind(sales[position])
        }

        inner class SalesHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer, View.OnClickListener {


            fun bind(sale: Sale) {
                itemSaleTitle.text = sale.title
                itemSaleInfo.text = sale.info
            }

            override fun onClick(v: View?) {
                itemClickListener.onClick(sales[adapterPosition])
            }

            init {
                containerView.setOnClickListener(this)
            }

        }

    }
}