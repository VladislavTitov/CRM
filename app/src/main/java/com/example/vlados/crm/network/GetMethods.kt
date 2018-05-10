package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.Good
import io.reactivex.Single
import retrofit2.http.GET

interface GetMethods {

    @GET("goods")
    fun getAllGoods() : Single<List<Good>>

}