package com.example.vlados.crm.ui.holders

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.vlados.crm.R
import com.example.vlados.crm.common.ItemInterface
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.presenters.DiscountsPresenter
import com.example.vlados.crm.ui.fragment.DiscountsFragment
import com.example.vlados.crm.ui.fragment.getDiscountsFragment
import kotlinx.android.synthetic.main.fragment_discounts_holder.*
import java.util.*

/**
 * Created by Daria Popova on 26.04.18.
 */
fun Context.getDiscountsHolderFragment(): Fragment {
    val fragment = DiscountsHolderFragment()
    return fragment
}


class DiscountsHolderFragment : MvpAppCompatFragment(), ItemInterface<Discount> {
    
    @InjectPresenter
    lateinit var presenter: DiscountsPresenter
    
    @ProvidePresenter
    fun providePresenter(): DiscountsPresenter {
        return DiscountsPresenter(navigator)
    }
    
    lateinit var adapter: DiscountsAdapter
    
    override fun setItems(items: List<Discount>) {
        adapter.setItems(items)
    }
    
    var navigator: Navigator? = null
    
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }
    
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_discounts_holder, container, false)
    }
    
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        adapter = DiscountsAdapter(childFragmentManager)
        sales_pager.adapter = adapter
        saleTabs.setupWithViewPager(sales_pager)
        presenter.onItemsReady()
    }
    
    override fun onDetach() {
        super.onDetach()
        navigator = null
    }
    
    inner class DiscountsAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        
        private val fragments = WeakHashMap<Int, Fragment>()
        
        fun setItems(items: List<Discount>) {
            fragments.forEach { (_, u: Fragment?) -> (u as DiscountsFragment).setItems(items) }
        }
        
        override fun getItem(position: Int): Fragment {
            var calledFragment = fragments[position]
            if (calledFragment != null) {
                return calledFragment
            }
            calledFragment = when (position) {
                1 -> context.getDiscountsFragment(false)
                0 -> context.getDiscountsFragment(true)
                else -> context.getDiscountsFragment(true)
            }
            fragments.put(position, calledFragment)
            return calledFragment
        }
        
        override fun getCount(): Int {
            return 2
        }
        
        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                1 -> getString(R.string.offered_sales_title)
                0 -> getString(R.string.approved_sales_title)
                else -> getString(R.string.offered_sales_title)
            }
        }
        
    }
}