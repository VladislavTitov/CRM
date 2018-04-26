package com.example.vlados.crm.common

import android.support.v7.util.DiffUtil

class GenericDiffUtilsCallback<T>(
        private val olds : List<T>,
        private val news : List<T>,
        private val itemsSame : (old : Int, new : Int) -> Boolean,
        private val contentsSame : (old : Int, new : Int) -> Boolean
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return olds.size
    }

    override fun getNewListSize(): Int {
        return news.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return itemsSame.invoke(oldItemPosition, newItemPosition)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return contentsSame.invoke(oldItemPosition, newItemPosition)
    }
}