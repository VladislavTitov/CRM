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
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.EditObserver
import com.example.vlados.crm.common.GenericDiffUtilsCallback
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Shop
import com.example.vlados.crm.presenters.ShopsPresenter
import com.example.vlados.crm.ui.edit.getShopEditFragment
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.fragment_shops.*
import kotlinx.android.synthetic.main.item_shops.*


fun Context.getShopsFragment(): Fragment {
    val fragment = ShopsFragment()
    return fragment
}

class ShopsFragment : MvpAppCompatFragment(), ItemInterface<Shop>, Navigator.InnerNavigator, EditObserver {


    override fun changeFab() {
        navigator?.setFabClickListener { onFabClick() }
    }
    
    override fun onResume() {
        super.onResume()
        changeFab()
    }
   
    lateinit var shopAdapter: ShopAdapter

//    public fun onClick() {
//        context.getShopEditFragment().show(fragmentManager, "");
//    }
    
    var navigator: Navigator? = null
    @InjectPresenter
    lateinit var presenter: ShopsPresenter
    
    @ProvidePresenter
    fun providePresenter(): ShopsPresenter {
        return ShopsPresenter(navigator)
    }
    
    
    public fun onFabClick() {
        context.getShopEditFragment().show(childFragmentManager, "");
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
        val view = inflater.inflate(R.layout.fragment_shops, container, false)
        return view
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }
    
    fun init() {
        shopAdapter = ShopAdapter{
            context.getShopEditFragment(it).show(childFragmentManager, "")
            return@ShopAdapter false
        }
        shopsRV.adapter = shopAdapter
        shopsRV.layoutManager = LinearLayoutManager(context)
        presenter.onItemsReady()
    }
    
    
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            changeFab()
            setItems(presenter.shops)
        }
    }
    
    
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    override fun setItems(items: List<Shop>) {
        shopAdapter.setItems(items)
    }

    override fun onEditEnd() {
        presenter.onItemsReady()
    }
    
    inner class ShopAdapter(private val onItemClickListener: (item: Shop) -> Boolean) :
            RecyclerView.Adapter<ShopAdapter.ShopHolder>() {
        
        var shops = listOf<Shop>()
        
        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShopHolder {
            val view = LayoutInflater.from(parent?.context)
                    .inflate(R.layout.item_shops, parent, false)
            
            return ShopHolder(view)
        }
        
        override fun getItemCount(): Int {
            return shops.size
        }
        
        override fun onBindViewHolder(holder: ShopHolder?, position: Int) {
            holder?.bind(shops[position], position)
        }
        
        fun setItems(new: List<Shop>) {
            val diffUtilsCallback = GenericDiffUtilsCallback<Shop>(shops, new, {oldPosition, newPosition ->
                shops[oldPosition].id == new[newPosition].id
            }, {oldPosition, newPosition ->
                val oldItem = shops[oldPosition]
                val newItem = new[newPosition]
                oldItem.id == newItem.id &&
                        oldItem.name == newItem.name &&
                        oldItem.address == newItem.address
            })
            val result = DiffUtil.calculateDiff(diffUtilsCallback, false)
            shops = new
            result.dispatchUpdatesTo(this)
        }
        
        inner class ShopHolder(override val containerView: View) :
                RecyclerView.ViewHolder(containerView), LayoutContainer {
            
            
            fun bind(shop: Shop, position: Int) {
                shopName.text = shop.name
                shopAddress.text = shop.address
                containerView.setOnClickListener {
                    onItemClickListener(shop)
                }
            }
            
            
        }
        
    }
}