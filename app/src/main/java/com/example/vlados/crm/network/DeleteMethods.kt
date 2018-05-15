package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.Good
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.Path

interface DeleteMethods {

    @DELETE("goods/{id}")
    fun deleteGood(@Path("id") id: Long): Single<Good>

}