package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.db.models.Review
import com.example.vlados.crm.db.models.User
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
    fun createGood(@Query("good[name]") name: String, @Query("good[price]") price: Int, @Query("good[kind]") kind: String) : Single<Boolean>
}