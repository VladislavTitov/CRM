package com.example.vlados.crm.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vlados.crm.db.models.Good
import com.example.vlados.crm.common.ItemInterface

@InjectViewState
class GoodsPresenter : MvpPresenter<ItemInterface<Good>>() {

    fun onGoodsRVReady() {
        val things = listOf(
                Good(1L, "Thing 1", 100, listOf("S", "L")),
                Good(2L, "Thing 2", 100, listOf("S", "L")),
                Good(3L, "Thing 3", 100, listOf("S", "L")),
                Good(4L, "Thing 4", 100, listOf("S", "L")),
                Good(5L, "Thing 5", 100, listOf("S", "L")),
                Good(6L, "Thing 6", 100, listOf("S", "L")),
                Good(7L, "Thing 7", 100, listOf("S", "L")),
                Good(8L, "Thing 8", 100, listOf("S", "L")),
                Good(9L, "Thing 9", 100, listOf("S", "L")),
                Good(10L, "Thing 10", 100, listOf("S", "L")),
                Good(11L, "Thing 11", 100, listOf("S", "L")),
                Good(12L, "Thing 12", 100, listOf("S", "L")),
                Good(13L, "Thing 13", 100, listOf("S", "L")),
                Good(14L, "Thing 14", 100, listOf("S", "L")),
                Good(15L, "Thing 15", 100, listOf("S", "L")),
                Good(16L, "Thing 16", 100, listOf("S", "L")),
                Good(17L, "Thing 17", 100, listOf("S", "L")),
                Good(18L, "Thing 18", 100, listOf("S", "L")),
                Good(19L, "Thing 19", 100, listOf("S", "L")),
                Good(20L, "Thing 20", 100, listOf("S", "L")),
                Good(21L, "Thing 21", 100, listOf("S", "L")),
                Good(22L, "Thing 22", 100, listOf("S", "L"))
        )
        viewState.setItems(things)
    }

    fun onItemClick(item : Good) : Boolean {
        return false
    }

}