package com.example.nikestore.data.repository.source

import com.example.nikestore.data.AddToCartResponse
import com.example.nikestore.data.CartItemCount
import com.example.nikestore.data.CartResponse
import com.example.nikestore.data.MessageResponse
import com.example.nikestore.modules.http.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(private val apiService: ApiService) : CartDataSource {

    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id",productId)
        }
    )

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCartItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}