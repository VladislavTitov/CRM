package com.example.vlados.crm.goodsandshops.shops

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.vlados.crm.R
import com.example.vlados.crm.common.Navigator

fun Fragment.getShopsFragment() : Fragment {
    val fragment = ShopsFragment()
    return fragment
}

class ShopsFragment : MvpAppCompatFragment() {

    var navigator: Navigator? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Navigator) {
            navigator = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_shops, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()

    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            navigator?.setFabClickListener {
                Snackbar.make(it, "Hi from Shops!", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

}