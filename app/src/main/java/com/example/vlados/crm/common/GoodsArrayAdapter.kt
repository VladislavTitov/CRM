package com.example.vlados.crm.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.vlados.crm.R
import com.example.vlados.crm.db.models.Good
import kotlinx.android.synthetic.main.good_spinner_item.view.*

/**
 * Created by Daria Popova on 14.05.18.
 */
class GoodsArrayAdapter(context: Context, var goods: List<Good>) :
        ArrayAdapter<Good>(context, 0, goods) {
    
    fun getPosition(goodId: Long?): Int {
        return goods.indexOfFirst { good -> good.id == goodId }
    }
    
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        
        val view = LayoutInflater.from(context)
                .inflate(R.layout.good_spinner_item, parent, false)
        
        val current = goods[position]
        view.good_spinner_name.text = current.name
        
        return view
    }
    
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.good_spinner_item, parent, false)
        
        val current = goods[position]
        view.good_spinner_name.text = current.name
        
        return view
    }
    
}