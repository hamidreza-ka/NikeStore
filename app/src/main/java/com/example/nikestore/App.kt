package com.example.nikestore

import android.app.Application
import android.os.Bundle
import com.example.nikestore.data.repository.*
import com.example.nikestore.data.repository.source.BannerRemoteDataSource
import com.example.nikestore.data.repository.source.CommentRemoteDataSource
import com.example.nikestore.data.repository.source.ProductLocalDataSource
import com.example.nikestore.data.repository.source.ProductRemoteDataSource
import com.example.nikestore.feature.main.MainViewModel
import com.example.nikestore.feature.main.ProductListAdapter
import com.example.nikestore.feature.product.ProductDetailViewModel
import com.example.nikestore.feature.product.comment.CommentListViewModel
import com.example.nikestore.modules.FrescoImageLoadingService
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.modules.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Fresco.initialize(this)

        val myModules = module {

            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }

            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }

            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }

            factory { ProductListAdapter(get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            viewModel { MainViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }


        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}