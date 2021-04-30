package com.example.nikestore.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "products")
@Parcelize
data class Product(
    val image: String,
    val price: Int,
    val discount: Int,
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    @SerializedName("previous_price")
    val previousPrice: Int,
    val status: Int
) : Parcelable {

    var isFavorite = false
}


const val SORT_LATEST = 0
const val SORT_POPULAR = 1
const val SORT_PRICE_DECS = 2
const val SORT_PRICE_ASC = 3
