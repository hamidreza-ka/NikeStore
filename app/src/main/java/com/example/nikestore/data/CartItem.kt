package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class CartItem(@SerializedName("cart_item_id")val cartItemId : Int, val count : Int, val product: Product)
