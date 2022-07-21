package com.example.nikestore.data.repository.source

import com.example.nikestore.data.AnalyticMD
import com.example.nikestore.data.OrderAnalyticModel
import com.example.nikestore.modules.http.AnalyticApiService
import io.reactivex.Completable
import io.reactivex.Single

class AnalyticRemoteDataSource(private val analyticApiService: AnalyticApiService) :
    AnalyticDataSource {

    override fun addView(productId: Int, name: String, price: String, image: String): Completable =
        analyticApiService.addView(productId, name, price, image)

    override fun getView(): Single<List<AnalyticMD>> = analyticApiService.getView()

    override fun addOrder(products: List<OrderAnalyticModel>): Completable =
        analyticApiService.addOrder(products)
}