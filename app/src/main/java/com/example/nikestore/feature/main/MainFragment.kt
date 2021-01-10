package com.example.nikestore.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.core.EXTRA_KEY_DATA
import com.example.nikestore.core.NikeFragment
import com.example.nikestore.core.convertDpToPixel
import com.example.nikestore.data.Product
import com.example.nikestore.feature.product.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainFragment : NikeFragment(), ProductListAdapter.OnProductClickListener {

    val mainViewModel: MainViewModel by viewModel()
    val latestProductListAdapter : ProductListAdapter by inject()
    val popularProductListAdapter : ProductListAdapter by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        latestProductsRv.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        latestProductsRv.adapter = latestProductListAdapter
        latestProductListAdapter.onProductClickListener = this

        popularProductsRv.layoutManager = LinearLayoutManager(requireContext(),RecyclerView.HORIZONTAL,false)
        popularProductsRv.adapter = popularProductListAdapter
        popularProductListAdapter.onProductClickListener = this


        mainViewModel.latestProductsLiveData.observe(viewLifecycleOwner) {
            Timber.i(it[0].toString())

            latestProductListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.popularProductsLiveData.observe(viewLifecycleOwner){
            popularProductListAdapter.products = it as ArrayList<Product>
        }

        mainViewModel.progressLiveData.observe(viewLifecycleOwner) {
            setProgressIndicator(it)
        }

        mainViewModel.bannerLiveData.observe(viewLifecycleOwner) {
            Timber.i(it[0].toString())

            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            bannerSliderViewPager.adapter = bannerSliderAdapter

            val viewPagerHeight = (((bannerSliderViewPager.measuredWidth - convertDpToPixel(32f,requireContext())) * 173) / 328).toInt()

            val layoutParams = bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            bannerSliderViewPager.layoutParams = layoutParams

            sliderIndicator.setViewPager2(bannerSliderViewPager)

        }

    }

    override fun onProductClick(product: Product) {
        startActivity(Intent(requireContext(),ProductDetailActivity::class.java).apply {
            this.putExtra(EXTRA_KEY_DATA,product)
        })
    }
}