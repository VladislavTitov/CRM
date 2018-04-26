package com.example.vlados.crm.main

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
import com.example.vlados.crm.goodsandshops.GoodsAndShopsHolder
import com.example.vlados.crm.goodsandshops.GoodsShopsHolder
import com.example.vlados.crm.honor.HonorBoard
import com.example.vlados.crm.honor.HonorBoardFragment
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
            SHOP_ADMIN -> nav_view.inflateMenu(R.menu.nav_menu_shop)
            NET_ADMIN -> nav_view.inflateMenu(R.menu.nav_menu_net)
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

        nav_view.getHeaderView(0).findViewById<TextView>(R.id.name).text = "Titov Vladislav Sergeevich"
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

            }
            R.id.nav_sales -> {

            }
            R.id.nav_accounts -> {

            }
            R.id.nav_reports -> {

            }
            R.id.nav_goods_and_shops -> {
                showGoodsAndShops()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun showHonorBoard() {
        val tag = HonorBoardFragment::class.java.name
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment != null && fragment.isAdded) {
            return
        }
        fragment = HonorBoard()
        supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .commit()
        toolbar.title = getString(R.string.title_honor_board)
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

    override fun setFabClickListener(l: (view: View) -> Unit) {
        fab.setOnClickListener(l)
    }

    override fun showSnack(text : String) {
        Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()
    }
}
