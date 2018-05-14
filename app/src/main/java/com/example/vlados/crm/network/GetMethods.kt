package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.*
import io.reactivex.Single
import retrofit2.http.GET

interface GetMethods {
    
    @GET("goods")
    fun getAllGoods(): Single<List<Good>>
    
    @GET("users")
    fun getAllUsers(): Single<List<User>>
    
    @GET("reviews")
    fun getAllReviews(): Single<List<Review>>
    
    @GET("shops")
    fun getAllShops(): Single<List<Shop>>
    
    @GET("discounts")
    fun getAllDiscounts(): Single<List<Discount>>
}