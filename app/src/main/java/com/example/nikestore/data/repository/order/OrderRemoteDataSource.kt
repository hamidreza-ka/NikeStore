package com.example.nikestore.data.repository.order

import com.example.nikestore.data.Checkout
import com.example.nikestore.data.SubmitOrderResult
import com.example.nikestore.modules.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class OrderRemoteDataSource(private val apiService: ApiService) : OrderDataSource {

    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        })
    }

    override fun checkout(orderId: Int): Single<Checkout> = apiService.checkout(orderId)

}