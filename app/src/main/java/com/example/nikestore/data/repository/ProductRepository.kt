package com.example.nikestore.data.repository

import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavoriteProducts(): Completable

    fun removeFromFavoriteProducts(): Completable
}