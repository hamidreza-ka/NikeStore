package com.example.nikestore.modules.http

import com.example.nikestore.data.AnalyticMD
import com.example.nikestore.data.OrderAnalyticModel
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AnalyticApiService {

    @POST("view")
    fun addView(
        @Query("product_id") productId: Int,
        @Query("name") name: String,
        @Query("price") price: String,
        @Query("image") image: String
    ): Completable

    @GET("view")
    fun getView(): Single<List<AnalyticMD>>

    @POST("order")
    fun addOrder(@Body products: List<OrderAnalyticModel>): Completable
}