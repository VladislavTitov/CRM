package com.example.vlados.crm.network

import com.example.vlados.crm.db.models.Discount
import com.example.vlados.crm.db.models.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path

/**
 * Created by Daria Popova on 12.05.18.
 */
interface PatchMethods {
    
    @PATCH("users/{id}")
    fun patchUser(@Path("id") id: Int?, @Body user: User?): Single<User>
    
    @PATCH("discounts/{id}")
    fun patchDiscount(@Path("id") id: Int?, @Body discount: Discount?): Single<Discount>
}