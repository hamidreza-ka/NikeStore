package com.example.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.Product

class ProductDetailViewModel(bundle: Bundle) : NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }
}