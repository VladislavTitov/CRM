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
import com.example.vlados.crm.R
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.NavMvpAppCompatFragment
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.presenters.DiscountsPresenter
import com.example.vlados.crm.ui.edit.getDiscountEditFragment
import com.example.vlados.crm.ui.holders.DiscountsHolderFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_discounts.*
import kotlinx.android.synthetic.main.item_discount.*

const val APPROVED_KEY = "approved"
fun Context.getDiscountsFragment(approved: Boolean = false): Fragment {
    val fragment = DiscountsFragment()
    val args = Bundle()
    args.putBoolean(APPROVED_KEY, approved)
    fragment.arguments = args
    return fragment
}

class DiscountsFragment : NavMvpAppCompatFragment(), ItemInterface<Discount> {
    
    var discountsAdapter: DiscountsAdapter? = null
    var navigator: Navigator? = null
    lateinit var presenter: DiscountsPresenter
    
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            changeFab()
        }
        if (lazy { presenter }.isInitialized())
            setItems(presenter.discounts)
    }
    
    override fun setItems(items: List<Discount>) {
        discountsAdapter?.setItems(presenter.discounts.filter { discount -> discount.approved == arguments.getBoolean(APPROVED_KEY) })
    }
    
    public fun onFabClick(discount: Discount? = null) {
        context.getDiscountEditFragment(discount).show(childFragmentManager, "");
    }
    
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        presenter = (parentFragment as DiscountsHolderFragment).presenter
        if (context is Navigator) {
            navigator = context
        }
    }
    
    
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_discounts, container, false)
        return view
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    
    override fun changeFab() {
        if (arguments.getBoolean(APPROVED_KEY))
            navigator?.hibeFab()
        else navigator?.setFabClickListener { onFabClick() }
    }
    
    
    fun init() {
        discountsAdapter = DiscountsAdapter()
        rvSales.adapter = discountsAdapter
        rvSales.layoutManager = LinearLayoutManager(context)
        setItems(presenter.discounts)
    }
    
    
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    
    inner class DiscountsAdapter() : RecyclerView.Adapter<DiscountsAdapter.DiscountsHolder>() {
        
        var discounts = listOf<Discount>()
        
        fun setItems(items: List<Discount>) {
            val diffUtilsCallback = GenericDiffUtilsCallback<Discount>(discounts, items,
                    { oldPosition, newPosition ->
                        discounts[oldPosition].id == items[newPosition].id
                    },
                    { oldPosition, newPosition ->
                        val oldItem = discounts[oldPosition]
                        val newItem = items[newPosition]
                        oldItem.from == newItem.from &&
                                oldItem.to == newItem.to &&
                                oldItem.approved == newItem.approved &&
                                oldItem.value == newItem.value &&
                                oldItem.type == newItem.type &&
                                oldItem.good == newItem.good &&
                                oldItem.formTitle() == newItem.formTitle()
                    }
            )
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            discounts = items
            result.dispatchUpdatesTo(this)
            notifyDataSetChanged()
        }
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DiscountsHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_discount, parent, false)
            
            return DiscountsHolder(view)
        }
        
        
        override fun getItemCount(): Int {
            return discounts.size
        }
        
        override fun onBindViewHolder(holder: DiscountsHolder?, position: Int) {
            holder?.bind(discounts[position], position)
        }
        
        
        inner class DiscountsHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(discount: Discount, position: Int) {
                itemSaleTitle.text = discount.formTitle()
                
                containerView.setOnClickListener { onFabClick(discount) }
            }
            
        }
        
    }
}