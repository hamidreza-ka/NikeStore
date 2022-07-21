package com.example.nikestore.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

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

@Parcelize
data class PurchaseDetail(
    var cartItems: List<CartItem>,
    var payablePrice: Int,
    var totalPrice: Int,
    var shippingCost: Int
): Parcelable
