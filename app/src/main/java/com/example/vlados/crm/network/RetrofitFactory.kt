@file:JvmName("RetrofitFactory")
package com.example.vlados.crm.network

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by vlados on 26.03.18.
 */

const val BASE_URL = "https://agile-journey-78814.herokuapp.com/v1/"

private fun create(): Retrofit {
    return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .build()
}

object ApiMethods {
    val get: GetMethods = create().create(GetMethods::class.java)
    val post: PostMethods = create().create(PostMethods::class.java)
}