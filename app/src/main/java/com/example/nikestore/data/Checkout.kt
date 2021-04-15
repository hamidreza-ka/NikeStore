package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class Checkout(

	@field:SerializedName("payment_status")
	val paymentStatus: String,

	@field:SerializedName("purchase_success")
	val purchaseSuccess: Boolean,

	@field:SerializedName("payable_price")
	val payablePrice: Int
)
