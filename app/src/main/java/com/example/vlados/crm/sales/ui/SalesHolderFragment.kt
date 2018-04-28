package com.example.vlados.crm.sales.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.common.Navigator
import kotlinx.android.synthetic.main.fragment_sales_holder.*
import java.util.*

/**
 * Created by Daria Popova on 26.04.18.
 */
fun Context.getSalesHolderFragment(): Fragment {
    val fragment = SalesHolderFragment()
    return fragment
}


class SalesHolderFragment : MvpAppCompatFragment() {

    var navigator: Navigator? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_sales_holder, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        sales_pager.adapter = SalesAdapter(childFragmentManager)
        saleTabs.setupWithViewPager(sales_pager)
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    inner class SalesAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragments = WeakHashMap<Int, Fragment>()

        override fun getItem(position: Int): Fragment {
            var calledFragment = fragments[position]
            if (calledFragment != null) {
                return calledFragment
            }
            calledFragment = context.getSalesFragment()
            fragments.put(position, calledFragment)
            return calledFragment
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> getString(R.string.offered_sales_title)
                1 -> getString(R.string.approved_sales_title)
                else -> getString(R.string.offered_sales_title)
            }
        }

    }
}