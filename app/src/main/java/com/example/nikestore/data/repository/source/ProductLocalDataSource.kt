package com.example.nikestore.data.repository.source

import androidx.room.*
import com.example.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource : ProductDataSource {

    override fun getProducts(sort : Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * FROM products")
    override fun getFavoriteProducts(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addToFavoriteProducts(product: Product): Completable

    @Delete
    override fun removeFromFavoriteProducts(product: Product): Completable
}