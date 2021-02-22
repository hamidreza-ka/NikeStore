package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class AddToCartResponse(

	@field:SerializedName("product_id")
	val productId: Int,

	@field:SerializedName("count")
	val count: Int,

	@field:SerializedName("id")
	val id: Int
)
