package com.example.nikestore.feature.favorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.data.Product
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.view.NikeImageView

class FavoriteProductsAdapter(
    val productList: MutableList<Product>,
    val favoriteProductEventListener: FavoriteProductEventListener,
    val imageLoadingService: ImageLoadingService
) :
    RecyclerView.Adapter<FavoriteProductsAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val titleTv = itemView.findViewById<TextView>(R.id.favoriteProductTitleTv)
        val productIv = itemView.findViewById<NikeImageView>(R.id.favoriteProductIv)

        fun bindProduct(product: Product) {
            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image)

            itemView.setOnClickListener {
                favoriteProductEventListener.onClick(product)
            }

            itemView.setOnLongClickListener {
                productList.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductEventListener.onLongClick(product)
                if (productList.isEmpty())
                    favoriteProductEventListener.onFavoriteProductIsEmpty()
                return@setOnLongClickListener false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_favorite_products, parent, false)
        )
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindProduct(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    interface FavoriteProductEventListener {

        fun onClick(product: Product)
        fun onLongClick(product: Product)
        fun onFavoriteProductIsEmpty()
    }
}