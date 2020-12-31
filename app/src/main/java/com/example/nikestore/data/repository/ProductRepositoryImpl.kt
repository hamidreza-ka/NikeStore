package com.example.nikestore.data.repository

import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductDataSource
) : ProductRepository {

    override fun getProducts(): Single<List<Product>> = remoteDataSource.getProducts()

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