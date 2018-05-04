package com.example.vlados.crm.common

import com.arellomobile.mvp.MvpAppCompatFragment

/**
 * Created by Daria Popova on 04.05.18.
 */
public abstract class NavMvpAppCompatFragment : MvpAppCompatFragment(), Navigator.InnerNavigator {

    override fun onResume() {
        super.onResume()
        changeFab()
    }
}