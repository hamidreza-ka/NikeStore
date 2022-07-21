package com.example.nikestore.data.repository.analytic

import com.example.nikestore.data.AnalyticMD
import com.example.nikestore.data.OrderAnalyticModel
import io.reactivex.Completable
import io.reactivex.Single

interface AnalyticRepository {

    fun addView(productId: Int, name: String, price: String, image: String) : Completable

    fun getView(): Single<List<AnalyticMD>>

    fun addOrder(products: List<OrderAnalyticModel>) : Completable
}