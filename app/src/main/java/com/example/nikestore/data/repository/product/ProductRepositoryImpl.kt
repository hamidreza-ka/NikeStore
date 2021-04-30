package com.example.nikestore.data.repository.product

import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(
    val remoteDataSource: ProductDataSource,
    val localDataSource: ProductDataSource
) : ProductRepository {

    override fun getProducts(sort: Int): Single<List<Product>> =
        localDataSource.getFavoriteProducts()
            .flatMap { favoriteProducts ->
                remoteDataSource.getProducts(sort).doOnSuccess { products ->
                    val favoritesProductIds = favoriteProducts.map { it.id }
                    products.forEach { product ->
                        if (favoritesProductIds.contains(product.id))
                            product.isFavorite = true
                    }
                }

            }

    override fun getFavoriteProducts(): Single<List<Product>> =
        localDataSource.getFavoriteProducts()


    override fun addToFavoriteProducts(product: Product): Completable =
        localDataSource.addToFavoriteProducts(product)


    override fun removeFromFavoriteProducts(product: Product): Completable =
        localDataSource.removeFromFavoriteProducts(product)
}