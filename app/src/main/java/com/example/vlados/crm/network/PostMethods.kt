package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.*
import com.example.vlados.crm.network.login.LoginResponse
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostMethods {
    
    @POST("")
    fun login(): Single<LoginResponse>

    @POST("users")
    fun postUser(@Body user: User?): Single<User>
    
    @POST("users/{user_id}/reviews")
    fun postReview(@Path("user_id") userId: Int, @Body review: Review): Single<Review>
    
    @POST("discounts")
    fun postDiscount(@Body discount: Discount?): Single<Discount>

    @POST("goods")
    fun createGood(@Body good: Good) : Single<Boolean>

    @POST("shops")
    fun createShop(@Body shop: Shop): Single<Boolean>
}