package com.example.vlados.crm.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.NavMvpAppCompatFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Sale
import com.example.vlados.crm.presenters.SalesPresenter
import com.example.vlados.crm.ui.edit.getSaleEditFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_sales.*
import kotlinx.android.synthetic.main.item_sale.*


fun Context.getSalesFragment(approved: Boolean = false): Fragment {
    val fragment = SalesFragment()
    val args = Bundle()
    args.putBoolean(SalesFragment.APPROVED_KEY, approved)
    fragment.arguments = args
    return fragment
}

class SalesFragment : NavMvpAppCompatFragment(), ItemInterface<Sale> {

    lateinit var salesAdapter: SalesAdapter
    var navigator: Navigator? = null
    @InjectPresenter
    lateinit var presenter: SalesPresenter

    @ProvidePresenter
    fun providePresenter(): SalesPresenter {
        return SalesPresenter()
    }


    companion object {
        const val APPROVED_KEY = "approved"
    }


    public fun onFabClick() {
        context.getSaleEditFragment().show(fragmentManager, "");
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
        val view = inflater.inflate(R.layout.fragment_sales, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }



    override fun changeFab() {
        navigator?.setFabClickListener { onFabClick() }  }



    fun init() {
        salesAdapter = SalesAdapter()
        rvSales.adapter = salesAdapter
        rvSales.layoutManager = LinearLayoutManager(context)
        presenter.onItemsReady()
    }


    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    override fun setItems(items: List<Sale>) {
        salesAdapter.setItems(items)
    }

    inner class SalesAdapter() : RecyclerView.Adapter<SalesAdapter.SalesHolder>() {

        var sales = listOf<Sale>()

        fun setItems(items: List<Sale>) {
            val diffUtilsCallback = GenericDiffUtilsCallback<Sale>(sales, items,
                    { oldPosition, newPosition ->
                        sales[oldPosition].id == items[newPosition].id
                    },
                    { oldPosition, newPosition ->
                        val oldItem = sales[oldPosition]
                        val newItem = items[newPosition]
                        oldItem.title == newItem.title &&
                                oldItem.info == newItem.info &&
                                oldItem.from == oldItem.from &&
                                oldItem.to == oldItem.to &&
                                oldItem.approved == oldItem.approved
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            sales = items
            result.dispatchUpdatesTo(this)
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): SalesHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_sale, parent, false)

            return SalesHolder(view)
        }


        override fun getItemCount(): Int {
            return sales.size
        }

        override fun onBindViewHolder(holder: SalesHolder?, position: Int) {
            holder?.bind(sales[position], position)
        }

        inner class SalesHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {


            fun bind(sale: Sale, position: Int) {
                itemSaleTitle.text = sale.title
                itemSaleInfo.text = sale.info
            }

        }

    }
}