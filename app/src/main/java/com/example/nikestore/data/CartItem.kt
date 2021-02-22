package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class CartItem(

    @field:SerializedName("cart_item_id")
    val cartItemId : Int,

    @field:SerializedName("count")
    var count : Int,

    @field:SerializedName("product")
    val product: Product)
