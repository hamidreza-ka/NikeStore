package com.example.nikestore.feature.common

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.core.formatPrice
import com.example.nikestore.core.implementSpringAnimationTrait
import com.example.nikestore.data.Product
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.view.NikeImageView
import java.lang.IllegalStateException

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(
    var viewType: Int = VIEW_TYPE_ROUND,
    val imageLoadingService: ImageLoadingService
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var productEventListener: ProductEventListener? = null

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
        val favoriteBtn = itemView.findViewById<ImageView>(R.id.favoriteBtn)


        fun bindProduct(product: Product) {

            titleTv.text = product.title
            imageLoadingService.load(productIv, product.image)
            currentPriceTv.text = formatPrice(product.price)
            previousPriceTv.text = formatPrice(product.previousPrice)
            previousPriceTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            itemView.implementSpringAnimationTrait()
            // this caused item view be clickable and can implement animation
            itemView.setOnClickListener {
                productEventListener?.onProductClick(product)
            }

            if (product.isFavorite)
                favoriteBtn.setImageResource(R.drawable.ic_favorite_fill)
            else
                favoriteBtn.setImageResource(R.drawable.ic_favorites_16dp)


            favoriteBtn.setOnClickListener {
                productEventListener?.onFavoriteBtnClicked(product)
                product.isFavorite = !product.isFavorite
                notifyItemChanged(adapterPosition)
            }
        }
    }

    override fun getItemViewType(position: Int): Int = viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layoutResId = when (viewType) {

            VIEW_TYPE_ROUND -> R.layout.item_product
            VIEW_TYPE_SMALL -> R.layout.item_product_small
            VIEW_TYPE_LARGE -> R.layout.item_product_large
            else -> throw IllegalStateException("viewType is not valid")
        }

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bindProduct(products[position])

    override fun getItemCount(): Int = products.size

    interface ProductEventListener {
        fun onProductClick(product: Product)
        fun onFavoriteBtnClicked(product: Product)
    }
}