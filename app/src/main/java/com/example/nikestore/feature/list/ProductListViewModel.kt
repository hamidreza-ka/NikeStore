package com.example.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.core.NikeCompletableObserver
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.product.ProductRepository
import io.reactivex.schedulers.Schedulers

class ProductListViewModel(var sort: Int, private val productRepository: ProductRepository) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProductList()
        selectedSortTitleLiveData.value = sortTitles[sort]
    }

    private fun getProductList() {

        progressLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }
            })
    }

    fun onSelectedSortChangedByUser(sort: Int) {
        this.sort = sort
        selectedSortTitleLiveData.value = sortTitles[sort]
        getProductList()
    }

    fun addProductToFavorites(product: Product) {

        if (product.isFavorite)
            productRepository.removeFromFavoriteProducts(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }

                })
        else
            productRepository.addToFavoriteProducts(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {

                    override fun onComplete() {
                        product.isFavorite = true
                    }

                })
    }
}