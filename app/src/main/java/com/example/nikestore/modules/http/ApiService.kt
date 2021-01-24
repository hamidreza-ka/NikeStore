package com.example.nikestore.modules.http

import com.example.nikestore.data.AddToCartResponse
import com.example.nikestore.data.Banner
import com.example.nikestore.data.Comment
import com.example.nikestore.data.Product
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort:String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners() : Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId : Int) : Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject) : Single<AddToCartResponse>
}