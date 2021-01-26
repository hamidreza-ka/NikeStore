package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class CartResponse(
    val cartItem: CartItem,
    @SerializedName("payable_price") val payablePrice: Int,
    @SerializedName("total_price") val totalPrice: Int,
    @SerializedName("shipping_cost") val shippingCost: Int
)
