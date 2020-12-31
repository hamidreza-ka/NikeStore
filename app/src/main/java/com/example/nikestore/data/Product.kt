package com.example.nikestore.data

data class Product(
	val image: String,
	val price: Int,
	val discount: Int,
	val id: Int,
	val title: String,
	val previousPrice: Int,
	val status: Int
)

