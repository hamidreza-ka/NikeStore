package com.example.nikestore.data.repository.source

import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavoriteProducts(): Completable

    fun removeFromFavoriteProducts(): Completable
}