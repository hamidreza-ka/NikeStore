package com.example.nikestore.feature.analytics

import android.os.Bundle
import android.view.View
import com.example.nikestore.R
import com.example.nikestore.core.NikeActivity
import kotlinx.android.synthetic.main.activity_analytics.*
import kotlinx.android.synthetic.main.activity_favorite_products.toolbarView
import kotlinx.android.synthetic.main.view_default_empty_state.*
import kotlinx.android.synthetic.main.view_loading.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject

class AnalyticsActivity : NikeActivity() {

    val viewModel: AnalyticsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        toolbarView.onBackButtonClickListener = View.OnClickListener { finish() }


        viewModel.analyticsLiveData.observe(this) {

            if (it.isNotEmpty()) {
                bestViewedProductsRv.adapter =
                    AnalyticsAdapter(it, get())

                bestViewedTv.visibility = View.VISIBLE

                bestOrderedProductsRv.adapter =
                    AnalyticsAdapter(it, get())

                bestOrderedTv.visibility = View.VISIBLE
            } else {
                showEmptyState(R.layout.view_default_empty_state)
                emptyStateMessageTv.text = getString(R.string.analytics_empty_state_message)
            }
        }

        viewModel.errorLiveData.observe(this){
            if (it){
            showEmptyState(R.layout.view_default_empty_state)
            emptyStateMessageTv.text = getString(R.string.analytics_empty_state_message)
            }
        }

        viewModel.progressLiveData.observe(this){
            setProgressIndicator(it)
        }
    }
}