package com.example.nikestore.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Banner(
	val linkType: Int,
	val image: String,
	val id: Int,
	val linkValue: String
) : Parcelable

