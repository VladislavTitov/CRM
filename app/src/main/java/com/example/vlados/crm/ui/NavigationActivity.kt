package com.example.vlados.crm.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.vlados.crm.*
import com.example.vlados.crm.common.Navigator
import com.example.vlados.crm.ui.fragment.*
import com.example.vlados.crm.ui.holders.DiscountsHolderFragment
import com.example.vlados.crm.ui.holders.GoodsAndShopsHolder
import com.example.vlados.crm.ui.holders.GoodsShopsHolder
import com.example.vlados.crm.ui.holders.getDiscountsHolderFragment
import com.example.vlados.crm.utils.getCurrentUser
import kotlinx.android.synthetic.main.activity_navigation.*

private const val ROLE_KEY = "role_key"

fun Context.NavigationActivity(role: String): Intent {
    return Intent(this, NavigationActivity::class.java).apply {
        putExtra(ROLE_KEY, role)
    }
}

class NavigationActivity : MvpAppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, Navigator {
  
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val roleStr = intent.extras.getString(ROLE_KEY, CASHIER)
        setContentView(R.layout.activity_navigation)
        when (roleStr) {
            CASHIER -> nav_view.inflateMenu(R.menu.nav_menu_cashier)
            MANAGER -> nav_view.inflateMenu(R.menu.nav_menu_shop)
            ADMIN -> nav_view.inflateMenu(R.menu.nav_menu_net)
        }
        
        setSupportActionBar(toolbar)
        
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        
        nav_view.setNavigationItemSelectedListener(this)
        
//        val user = User(role = roleStr)
//        saveCurrentUser(user)
        
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.name).text = getCurrentUser()?.fullName
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.role).text = getString(mapToTitleId[roleStr]!!)
        
        nav_view.setCheckedItem(R.id.nav_honor)
        showHonorBoard()
    }
    
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_honor -> {
                showHonorBoard()
            }
            R.id.nav_chat -> {
                showDialogs()
            }
            R.id.nav_sales -> {
                showSales()
            }
            R.id.nav_accounts -> {
                showAccounts()
            }
            R.id.nav_reports -> {
                showReports()
            }
            R.id.nav_goods_and_shops -> {
                showGoodsAndShops()
            }
        }
        
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    
    private fun showDialogs(){
        val tag = DialogsFragment::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = getDialogsFragmnet()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_dialogs)
    }

    
    private fun showHonorBoard() {
        val tag = ReviewsFragment::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = getReviewFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_honor_board)
    }
    
    private fun showAccounts() {
        val tag = UserFragment::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = getUsersFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_accounts)
    }
    
    private fun showSales() {
        val tag = DiscountsHolderFragment::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = getDiscountsHolderFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_sales)
    }
    
    private fun showGoodsAndShops() {
        val tag = GoodsShopsHolder::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = GoodsAndShopsHolder()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_goods_and_shops)
    }

    private fun showReports() {
        val tag = ReportsFragment::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = getReportsFragment()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_reports)
    }
    
    override fun setFabClickListener(l: (view: View) -> Unit) {
        fab.visibility = View.VISIBLE
        fab.setOnClickListener(l)
    }
    
    override fun hibeFab() {
        fab.visibility = View.GONE
        fab.setOnClickListener(null)
    }
    
    override fun showSnack(text: String) {
        Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
    }
}
