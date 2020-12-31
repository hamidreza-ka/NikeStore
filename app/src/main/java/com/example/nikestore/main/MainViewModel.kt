package com.example.nikestore.main

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.ProductRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(productRepository: ProductRepository) : NikeViewModel() {

    val productsLiveData  = MutableLiveData<List<Product>>()

    init {

        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Product>> {

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }


            })
    }
}