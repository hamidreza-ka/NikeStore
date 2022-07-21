package com.example.nikestore.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CartItem(

    @field:SerializedName("cart_item_id")
    val cartItemId: Int,

    @field:SerializedName("count")
    var count: Int,

    @field:SerializedName("product")
    val product: Product,

    var changeCountProgressBarIsVisible: Boolean = false
) : Parcelable
