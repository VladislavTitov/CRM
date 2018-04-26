package com.example.vlados.crm.goodsandshops

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
import com.example.vlados.crm.goodsandshops.goods.getGoodsFragment
import com.example.vlados.crm.goodsandshops.shops.getShopsFragment
import kotlinx.android.synthetic.main.fragment_goods_and_shops_holder.*
import java.util.*

fun Context.GoodsAndShopsHolder(): Fragment {
    val holder = GoodsShopsHolder()
    return holder
}

class GoodsShopsHolder : MvpAppCompatFragment() {

    var navigator: Navigator? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_goods_and_shops_holder, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        pager.adapter = GoodsShopsAdapter(fragmentManager)
        tabs.setupWithViewPager(pager)
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    inner class GoodsShopsAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {

        private val fragments = WeakHashMap<Int, Fragment>()

        override fun getItem(position: Int): Fragment {
            var calledFragment = fragments[position]
            if (calledFragment != null) {
                return calledFragment
            }
            calledFragment = when (position) {
                0 -> getGoodsFragment()
                1 -> getShopsFragment()
                else -> getGoodsFragment()
            }
            fragments.put(position, calledFragment)
            return calledFragment
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence {
            return when (position) {
                0 -> getString(R.string.title_goods)
                1 -> getString(R.string.title_shops)
                else -> getString(R.string.title_goods)
            }
        }

    }
}