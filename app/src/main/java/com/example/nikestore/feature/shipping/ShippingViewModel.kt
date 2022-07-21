package com.example.nikestore.feature.shipping

import android.util.Log
import com.example.nikestore.core.NikeCompletableObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.OrderAnalyticModel
import com.example.nikestore.data.SubmitOrderResult
import com.example.nikestore.data.repository.analytic.AnalyticRepository
import com.example.nikestore.data.repository.order.OrderRepository
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers


const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"

class ShippingViewModel(private val orderRepository: OrderRepository, private val analyticRepository: AnalyticRepository) : NikeViewModel() {

    fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {

        return orderRepository.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
    }

    fun addOrderToAnalyticDb(
        products: List<OrderAnalyticModel>
    ) {
        analyticRepository.addOrder(products)
            .subscribeOn(Schedulers.io())
            .subscribe(object : NikeCompletableObserver(compositeDisposable){
                override fun onComplete() {
                    Log.e("", "onComplete: order added")
                }

            })
    }
}