package com.example.nikestore.feature.analytics

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.AnalyticMD
import com.example.nikestore.data.Product
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.view.NikeImageView

class AnalyticsAdapter(
    val analyticsMD: List<AnalyticMD>,
    val imageLoadingService: ImageLoadingService
) :
    RecyclerView.Adapter<AnalyticsAdapter.AnalyticsViewHolder>() {

    inner class AnalyticsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv = itemView.findViewById<TextView>(R.id.productTitleTv)
        val countViewTv = itemView.findViewById<TextView>(R.id.countViewTv)
        val productIv = itemView.findViewById<NikeImageView>(R.id.productIv)

        fun bindProduct(analyticMD: AnalyticMD) {
            titleTv.text = analyticMD.name
            countViewTv.text = "${analyticMD.sumView.toString()} عدد"
            imageLoadingService.load(productIv, analyticMD.image!!)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnalyticsViewHolder {
        return AnalyticsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_product_analytic, parent, false)
        )
    }

    override fun onBindViewHolder(holder: AnalyticsViewHolder, position: Int) {
        holder.bindProduct(analyticsMD[position])
    }

    override fun getItemCount(): Int = analyticsMD.size
}