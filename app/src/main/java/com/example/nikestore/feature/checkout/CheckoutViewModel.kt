package com.example.nikestore.feature.checkout

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.Checkout
import com.example.nikestore.data.repository.order.OrderRepository

class CheckoutViewModel(orderId: Int, orderRepository: OrderRepository) : NikeViewModel() {

    val checkoutLiveData = MutableLiveData<Checkout>()


    init {
        orderRepository.checkout(orderId)
            .asyncNetworkRequest()
            .subscribe(object : NikeSingleObserver<Checkout>(compositeDisposable) {
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }
            })
    }

}