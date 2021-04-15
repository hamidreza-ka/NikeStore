package com.example.nikestore.feature.shipping

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nikestore.R
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.data.PurchaseDetail
import com.example.nikestore.feature.cart.CartItemAdapter
import kotlinx.android.synthetic.main.activity_shipping.*
import java.lang.IllegalStateException

class ShippingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shipping)

        val purchaseDetail =
            intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
                ?: throw IllegalStateException("purchase detail cannot be null")

        val viewHolder = CartItemAdapter.PurchaseDetailViewHolder(purchaseDetailView)
        viewHolder.bindPurchaseDetail(
            purchaseDetail.totalPrice,
            purchaseDetail.shippingCost,
            purchaseDetail.payablePrice
        )
    }
}