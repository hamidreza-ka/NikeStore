package com.example.nikestore.data.repository.order

import com.example.nikestore.data.Checkout
import com.example.nikestore.data.SubmitOrderResult
import io.reactivex.Single

class OrderRepositoryImpl(private val orderDataSource: OrderDataSource) : OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> =
        orderDataSource.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)

    override fun checkout(orderId: Int): Single<Checkout> = orderDataSource.checkout(orderId)
}