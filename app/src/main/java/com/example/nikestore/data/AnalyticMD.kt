package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class AnalyticMD(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("productId")
	val productId: Int? = null,

	@field:SerializedName("price")
	val price: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("sumView")
	val sumView: Int? = null
)
