package com.example.nikestore.feature.cart

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.NikeCompletableObserver
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.*
import com.example.nikestore.data.repository.CartRepository
import io.reactivex.Completable
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

class CartViewModel(private val cartRepository: CartRepository) : NikeViewModel() {

    val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()

    private fun getCartItems() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressLiveData.value = true
            cartRepository.get()
                .asyncNetworkRequest()
                .doFinally { progressLiveData.value = false }
                .subscribe(object : NikeSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cartItems.isNotEmpty())
                            cartItemsLiveData.value = t.cartItems

                        purchaseDetailLiveData.value =
                            PurchaseDetail(t.payablePrice, t.totalPrice, t.shippingCost)
                    }
                })
        }
    }

    fun removeItemFromCart(cartItem: CartItem): Completable =
        cartRepository.remove(cartItem.cartItemId)
            .doOnSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()

    fun increaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cartItemId, cartItem.count + 1)
            .doOnSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()

    fun decreaseCartItemCount(cartItem: CartItem): Completable =
        cartRepository.changeCount(cartItem.cartItemId, cartItem.count - 1)
            .doOnSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()

    fun refresh() = getCartItems()

    private fun calculateAndPublishPurchaseDetail() {
        cartItemsLiveData.value?.let { cartItems ->

            purchaseDetailLiveData.value?.let { purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0

                cartItems.forEach {
                    totalPrice += it.product.price * it.count
                    payablePrice += (it.product.price - it.product.discount) * it.count
                }

                purchaseDetail.payablePrice = payablePrice
                purchaseDetail.totalPrice = totalPrice

                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }
}