package com.example.nikestore.feature.product

import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nikestore.R
import com.example.nikestore.core.*
import com.example.nikestore.data.Comment
import com.example.nikestore.feature.product.comment.CommentListActivity
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.view.scroll.ObservableScrollViewCallbacks
import com.example.nikestore.view.scroll.ScrollState
import com.google.android.material.snackbar.Snackbar
import io.reactivex.CompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.item_product.*

import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class ProductDetailActivity : NikeActivity() {

    private val viewModel: ProductDetailViewModel by viewModel { parametersOf(intent.extras) }
    private val imageLoadingService: ImageLoadingService by inject()
    private val commentAdapter = CommentAdapter()
    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        viewModel.productLiveData.observe(this) {
            imageLoadingService.load(productDetailIv, it.image)
            titleTv.text = it.title
            previousPriceDetailTv.text = formatPrice(it.price + it.discount)
            previousPriceDetailTv.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            currentPriceDetailTv.text = formatPrice(it.price)
            toolbarTitleTv.text = it.title
        }

        viewModel.progressLiveData.observe(this) {
            setProgressIndicator(it)
        }

        viewModel.commentsLiveData.observe(this) {
            Timber.i(it[0].toString())
            commentAdapter.comments = it as ArrayList<Comment>

            if (it.size > 3) {

                viewAllCommentsBtn.visibility = View.VISIBLE

                viewAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, viewModel.productLiveData.value!!.id)
                    })
                }
            }
        }

        initViews()

    }

    private fun initViews() {

        commentsRv.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        commentsRv.adapter = commentAdapter

        backIv.setOnClickListener { finish() }

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
                    //    Timber.i("Title : ${productIvHeight.toFloat() / (productIvHeight.toFloat() - (scrollY.toFloat() * 2 ))}")
                    // productTitleTv.alpha = productIvHeight.toFloat() / scrollY.toFloat()
                    productImageView.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }


        addToCartBtn.setOnClickListener {
            viewModel.onAddToCartBtnClicked()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        showSnackBar(getString(R.string.successAddToCart))
                    }
                })
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}