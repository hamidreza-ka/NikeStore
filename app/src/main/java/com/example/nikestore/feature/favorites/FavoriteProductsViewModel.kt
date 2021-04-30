package com.example.nikestore.feature.favorites

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.NikeCompletableObserver
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.product.ProductRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductsViewModel(private val productRepository: ProductRepository) :
    NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.postValue(t)
                }

            })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.removeFromFavoriteProducts(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    product.isFavorite = false
                }

            })
    }
}