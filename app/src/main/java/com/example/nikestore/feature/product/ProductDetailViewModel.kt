package com.example.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.Comment
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.CartRepository
import com.example.nikestore.data.repository.CommentRepository
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(bundle: Bundle, commentRepository: CommentRepository, val cartRepository: CartRepository) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {

        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)

        progressLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .asyncNetworkRequest()
            .doFinally { progressLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {

                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

            })
    }


    fun onAddToCartBtnClicked() : Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()
}