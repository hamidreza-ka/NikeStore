package com.example.nikestore.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
	val image: String,
	val price: Int,
	val discount: Int,
	val id: Int,
	val title: String,
	@SerializedName("previous_price")
	val previousPrice: Int,
	val status: Int
) : Parcelable


const val SORT_LATEST = 0
const val SORT_POPULAR = 1
const val SORT_PRICE_DECS = 2
const val SORT_PRICE_ASC = 3
