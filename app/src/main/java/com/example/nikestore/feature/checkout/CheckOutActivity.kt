package com.example.nikestore.feature.checkout

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nikestore.R
import com.example.nikestore.core.EXTRA_KEY_ID
import com.example.nikestore.core.formatPrice
import kotlinx.android.synthetic.main.activity_check_out.*
import kotlinx.android.synthetic.main.item_purchase_details.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : AppCompatActivity() {

    val viewModel: CheckoutViewModel by viewModel {
        val uri: Uri? = intent.data
        if (uri != null)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_out)

        viewModel.checkoutLiveData.observe(this) {

            purchaseStatusTv.text = if (it.purchaseSuccess) "خرید با موفقیت انجام شد" else "خرید ناموفق"
            orderStatusTv.text = it.paymentStatus
            orderPriceTv.text = formatPrice(it.payablePrice)

        }

        checkoutToolbar.onBackButtonClickListener = View.OnClickListener {
            finish()
        }
    }
}