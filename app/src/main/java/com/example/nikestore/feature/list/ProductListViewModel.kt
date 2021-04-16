package com.example.nikestore.feature.list

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.R
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.product.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
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
}