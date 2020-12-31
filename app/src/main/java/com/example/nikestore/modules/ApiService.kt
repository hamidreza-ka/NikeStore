package com.example.nikestore.modules

import com.example.nikestore.data.Product
import io.reactivex.Single
import retrofit2.http.GET

interface ApiService {

    @GET("product/list")
    fun getProducts(): Single<List<Product>>
}