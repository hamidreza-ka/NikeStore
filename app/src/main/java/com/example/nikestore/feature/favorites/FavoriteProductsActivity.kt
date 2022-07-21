package com.example.nikestore.feature.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.NikeActivity
import com.example.nikestore.data.Product
import com.example.nikestore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.activity_favorite_products.*
import kotlinx.android.synthetic.main.view_default_empty_state.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class FavoriteProductsActivity : NikeActivity(),
    FavoriteProductsAdapter.FavoriteProductEventListener {

    val viewModel: FavoriteProductsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_products)

        toolbarView.onBackButtonClickListener = View.OnClickListener { finish() }

        helpBtn.setOnClickListener {
            showSnackBar(getString(R.string.favorites_help_message))
        }



        viewModel.productsLiveData.observe(this) {

            if (it.isNotEmpty()) {
                favoriteProductsRv.layoutManager =
                    LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                favoriteProductsRv.adapter =
                    FavoriteProductsAdapter(it as MutableList<Product>, this, get())
            }
            else {
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text = getString(R.string.favorites_empty_state_message)
            }
        }

    }

    override fun onClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onLongClick(product: Product) {
        viewModel.removeFromFavorites(product)
    }

    override fun onFavoriteProductIsEmpty() {
        showEmptyState(R.layout.view_default_empty_state)
        emptyStateMessageTv.text = getString(R.string.favorites_empty_state_message)
    }
}