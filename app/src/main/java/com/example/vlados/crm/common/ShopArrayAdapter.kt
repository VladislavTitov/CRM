package com.example.vlados.crm.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.vlados.crm.R
import com.example.vlados.crm.db.models.Shop
import kotlinx.android.synthetic.main.custom_spinner_item.view.*

/**
 * Created by Daria Popova on 15.05.18.
 */
class ShopArrayAdapter(context: Context, var shops: List<Shop>) :
        ArrayAdapter<Shop>(context, 0, shops) {
    
    fun getPosition(shopId: Long?): Int {
        return shops.indexOfFirst { shop -> shop.id == shopId }
    }
    
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        
        val view = LayoutInflater.from(context)
                .inflate(R.layout.custom_spinner_item, parent, false)
        
        val current = shops[position]
        view.custom_spinner_name.text = current.name
        
        return view
    }
    
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.custom_spinner_item, parent, false)
        
        val current = shops[position]
        view.custom_spinner_name.text = current.name
        
        return view
    }
    
}