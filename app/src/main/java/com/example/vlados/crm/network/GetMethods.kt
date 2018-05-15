package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GetMethods {
    
    @GET("goods")
    fun getAllGoods(): Single<MutableList<Good>>
    
    @GET("users")
    fun getAllUsers(): Single<List<User>>
    
    @GET("users")
    fun getRoledUsers(@Query("role") role: String): Single<List<User>>
    
    @GET("reviews")
    fun getAllReviews(): Single<List<Review>>
    
    @GET("shops")
    fun getAllShops(): Single<List<Shop>>
    
    @GET("discounts")
    fun getAllDiscounts(): Single<List<Discount>>
    
    @GET("messages")
    fun getAllFromMessages(@Query("from") from: Long?): Single<List<Message>>
    
    @GET("messages")
    fun getAllToMessages(@Query("to") to: Long?): Single<List<Message>>
    
    @GET("messages")
    fun getAllFromToMessages(@Query("to") to: Long?, @Query("from") from: Long?):
            Single<List<Message>>
}