package com.example.nikestore.feature.list

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.nikestore.R
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.NikeActivity
import com.example.nikestore.data.Product
import com.example.nikestore.feature.common.ProductListAdapter
import com.example.nikestore.feature.common.VIEW_TIPE_LARGE
import com.example.nikestore.feature.common.VIEW_TYPE_SMALL
import com.example.nikestore.feature.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_product_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductListActivity : NikeActivity(), ProductListAdapter.OnProductClickListener {

    val viewModel: ProductListViewModel by viewModel {
        parametersOf(
            intent.extras!!.getInt(
                EXTRA_KEY_DATA
            )
        )
    }
    val productListAdapter: ProductListAdapter by inject { parametersOf(VIEW_TYPE_SMALL) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        val gridLayoutManager = GridLayoutManager(this, 2)
        productListRv.layoutManager = gridLayoutManager
        productListRv.adapter = productListAdapter
        productListAdapter.onProductClickListener = this

        toolbarView.onBackButtonClickListener = View.OnClickListener { finish() }

        viewModel.selectedSortTitleLiveData.observe(this) {
            selectedSortTv.text = getString(it)
        }

        viewModel.progressLiveData.observe(this) {
            setProgressIndicator(it)
        }

        gridIv.setOnClickListener {

            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                gridIv.setImageResource(R.drawable.ic_view_type_large)
                productListAdapter.viewType = VIEW_TIPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
            } else {
                gridIv.setImageResource(R.drawable.ic_grid)
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
            }
        }

        sortBtn.setOnClickListener {

            val dialog = MaterialAlertDialogBuilder(this)
                .setTitle(getString(R.string.sort))
                .setSingleChoiceItems(
                    R.array.sortTitlesArray,
                    viewModel.sort
                ) { dialog, selectedSortIndex ->
                    viewModel.onSelectedSortChangedByUser(selectedSortIndex)
                    dialog.dismiss()
                }

            dialog.show()
        }

        viewModel.productLiveData.observe(this) {
            Timber.i(it[1].toString())
            productListAdapter.products = it as ArrayList<Product>

        }

    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}