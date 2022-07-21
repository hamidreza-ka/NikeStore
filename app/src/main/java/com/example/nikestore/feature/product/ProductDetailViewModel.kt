package com.example.nikestore.feature.product

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.Comment
import com.example.nikestore.data.Product
import com.example.nikestore.data.repository.analytic.AnalyticRepository
import com.example.nikestore.data.repository.cart.CartRepository
import com.example.nikestore.data.repository.comment.CommentRepository
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(
    bundle: Bundle,
    commentRepository: CommentRepository,
    private val cartRepository: CartRepository,
    analyticRepository: AnalyticRepository
) :
    NikeViewModel() {

    var disposable: Disposable? = null

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

        analyticRepository.addView(
            productLiveData.value!!.id,
            productLiveData.value!!.title,
            productLiveData.value!!.price.toString(),
            productLiveData.value!!.image
        )
            .subscribeOn(Schedulers.io())
            .subscribe(object : CompletableObserver {
                override fun onSubscribe(d: Disposable) {
                    disposable = d
                }

                override fun onComplete() {
                    Log.e("", "onComplete: view added")
                }

                override fun onError(e: Throwable) {
                }

            })
    }


    fun onAddToCartBtnClicked(): Completable =
        cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()

    override fun onCleared() {
        super.onCleared()
        disposable?.let {
            if (!it.isDisposed)
                it.dispose()
        }
    }
}