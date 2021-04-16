package com.example.nikestore.feature.shipping

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nikestore.R
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.EXTRA_KEY_ID
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.openUrlInCustomTab
import com.example.nikestore.data.PurchaseDetail
import com.example.nikestore.data.SubmitOrderResult
import com.example.nikestore.feature.cart.CartItemAdapter
import com.example.nikestore.feature.checkout.CheckOutActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_shipping.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.IllegalStateException

class ShippingActivity : AppCompatActivity() {

    val viewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()

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

        val onClickListener = View.OnClickListener {
            viewModel.submitOrder(
                firstNameEt.text.toString(),
                lastNameEt.text.toString(),
                postalCodeEt.text.toString(),
                phoneNumberEt.text.toString(),
                addressEt.text.toString(),
                if (it.id == R.id.onlinePaymentbtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD
            )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeSingleObserver<SubmitOrderResult>(compositeDisposable) {
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bankGatewayUrl.isNotEmpty())
                            openUrlInCustomTab(this@ShippingActivity, t.bankGatewayUrl)
                        else
                            startActivity(Intent(this@ShippingActivity, CheckOutActivity::class.java).apply {
                                putExtra(EXTRA_KEY_ID, t.orderId)
                            })

                        finish()

                    }

                })
        }

        onlinePaymentbtn.setOnClickListener(onClickListener)
        codBtn.setOnClickListener(onClickListener)

    }
}