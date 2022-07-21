package com.example.nikestore.data.repository.analytic

import com.example.nikestore.data.AnalyticMD
import com.example.nikestore.data.OrderAnalyticModel
import com.example.nikestore.data.repository.source.AnalyticDataSource
import io.reactivex.Completable
import io.reactivex.Single

class AnalyticRepositoryImpl(private val analyticDataSource: AnalyticDataSource) :
    AnalyticRepository {

    override fun addView(productId: Int, name: String, price: String, image: String): Completable =
        analyticDataSource.addView(productId, name, price, image)

    override fun getView(): Single<List<AnalyticMD>> =
        analyticDataSource.getView()

    override fun addOrder(products: List<OrderAnalyticModel>): Completable =
        analyticDataSource.addOrder(products)

}