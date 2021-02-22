package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class CartResponse(

    @field:SerializedName("cart_items")
    val cartItems: List<CartItem>,

    @field:SerializedName("payable_price")
    val payablePrice: Int,

    @field:SerializedName("total_price")
    val totalPrice: Int,

    @field:SerializedName("shipping_cost")
    val shippingCost: Int
)

data class PurchaseDetail(
    var payablePrice: Int,
    var totalPrice: Int,
    var shippingCost: Int
)
