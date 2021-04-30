package com.example.nikestore.data.repository.source

import com.example.nikestore.data.Product
import com.example.nikestore.modules.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {

    override fun getProducts(sort : Int): Single<List<Product>> = apiService.getProducts(sort.toString())

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavoriteProducts(product: Product): Completable {
        TODO("Not yet implemented")
    }

    override fun removeFromFavoriteProducts(product: Product): Completable {
        TODO("Not yet implemented")
    }
}