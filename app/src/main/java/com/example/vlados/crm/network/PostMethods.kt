package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.*
import com.example.vlados.crm.network.login.LoginRequest
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface PostMethods {
    
    @POST("login")
    fun login(@Body body: LoginRequest): Single<User>

    @POST("users")
    fun postUser(@Body user: User?): Single<User>
    
    @POST("users/{user_id}/reviews")
    fun postReview(@Path("user_id") userId: Long?, @Body review: Review): Single<Review>
    
    @POST("discounts")
    fun postDiscount(@Body discount: Discount?): Single<Discount>

    @POST("goods")
    fun createGood(@Body good: Good) : Single<Boolean>

    @POST("shops")
    fun createShop(@Body shop: Shop): Single<Boolean>
    
    @POST("messages")
    fun postMessage(@Body message: Message): Single<Message>
}