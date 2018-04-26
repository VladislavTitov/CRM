package com.example.vlados.crm.goodsandshops.goods

import com.arellomobile.mvp.MvpView
import com.example.vlados.crm.db.models.Good

interface GoodsInterface : MvpView {

    fun setGoods(goods : List<Good>)

}