package com.example.nikestore.feature.product

import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.core.formatPrice
import com.example.nikestore.data.Comment
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import kotlinx.android.synthetic.main.activity_product_detail.*

import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : AppCompatActivity() {

    private val viewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    val imageLoadingService: ImageLoadingService by inject()
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        viewModel.productLiveData.observe(this) {
            imageLoadingService.load(productDetailIv, it.image)
            titleTv.text = it.title
            previousPriceDetailTv.text = formatPrice(it.previousPrice)
            previousPriceDetailTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceDetailTv.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title
        }

        viewModel.commentsLiveData.observe(this) {
            Timber.i(it[0].toString())
            commentAdapter.comments = it as ArrayList<Comment>
        }


    }

    fun initViews() {

        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val commentAdapter = CommentAdapter()
        commentsRv.adapter = commentAdapter

        productDetailIv.post {

            val productIvHeight = productDetailIv.height
            val toolbar = toolbarView
            val productImageView = productDetailIv

            observableScrollView.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {

                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbar.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }
    }
}