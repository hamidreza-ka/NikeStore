package com.example.nikestore.feature.main

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.core.formatPrice
import com.example.nikestore.core.implementSpringAnimationTrait
import com.example.nikestore.data.Product
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.view.NikeImageView

class ProductListAdapter(val imageLoadingService: ImageLoadingService) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var products = ArrayList<Product>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv = itemView.findViewById<TextView>(R.id.productTitleTv)
        val productIv = itemView.findViewById<NikeImageView>(R.id.productIv)
        val currentPriceTv = itemView.findViewById<TextView>(R.id.currentPriceTv)
        val previousPriceTv = itemView.findViewById<TextView>(R.id.previousPriceTv)


        fun bindProduct(product: Product) {

            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image)
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previousPrice)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.implementSpringAnimationTrait()
            // this caused item view be clickable and can implement animation
            itemView.setOnClickListener {  }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size
}