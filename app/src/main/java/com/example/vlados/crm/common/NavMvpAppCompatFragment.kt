package com.example.vlados.crm.common

import com.arellomobile.mvp.MvpAppCompatFragment


public abstract class NavMvpAppCompatFragment : MvpAppCompatFragment(), Navigator.InnerNavigator {
    
    override fun onResume() {
        super.onResume()
        changeFab()
    }
}