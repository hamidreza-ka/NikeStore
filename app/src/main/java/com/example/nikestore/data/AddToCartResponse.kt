package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class AddToCartResponse(val id: Int, @SerializedName("product_id")val productId: Int, val count: Int)
