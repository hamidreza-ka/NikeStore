package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class SubnitOrderResult(

	@field:SerializedName("bank_gateway_url")
	val bankGatewayUrl: String,

	@field:SerializedName("order_id")
	val orderId: Int
)
