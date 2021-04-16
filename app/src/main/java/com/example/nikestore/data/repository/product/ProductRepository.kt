package com.example.nikestore.data.repository.product

import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(sort : Int): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavoriteProducts(): Completable

    fun removeFromFavoriteProducts(): Completable
}