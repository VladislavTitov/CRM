package com.example.vlados.crm.network

import com.example.vlados.crm.network.login.LoginResponse
import io.reactivex.Single
import retrofit2.http.POST

interface PostMethods {

    @POST("")
    fun login(): Single<LoginResponse>

}