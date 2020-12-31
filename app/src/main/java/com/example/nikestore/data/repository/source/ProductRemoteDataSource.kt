package com.example.nikestore.data.repository.source

import com.example.nikestore.data.Product
import com.example.nikestore.modules.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService) : ProductDataSource {

    override fun getProducts(): Single<List<Product>> = apiService.getProducts()

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavoriteProducts(): Completable {
        TODO("Not yet implemented")
    }

    override fun removeFromFavoriteProducts(): Completable {
        TODO("Not yet implemented")
    }
}