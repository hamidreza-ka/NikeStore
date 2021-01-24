package com.example.nikestore.data.repository

import com.example.nikestore.data.AddToCartResponse
import com.example.nikestore.data.CartItemCount
import com.example.nikestore.data.CartResponse
import com.example.nikestore.data.MessageResponse
import com.example.nikestore.data.repository.source.CartRemoteDataSource
import io.reactivex.Single

class CartRepositoryImpl(private val remoteDataSource: CartRemoteDataSource) : CartRepository {

    override fun addToCart(productId: Int): Single<AddToCartResponse> = remoteDataSource.addToCart(productId)

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