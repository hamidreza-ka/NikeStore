 package com.example.nikestore

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import androidx.room.Room
import com.example.nikestore.data.db.AppDatabase
import com.example.nikestore.data.repository.*
import com.example.nikestore.data.repository.banner.BannerRepository
import com.example.nikestore.data.repository.banner.BannerRepositoryImpl
import com.example.nikestore.data.repository.cart.CartRepository
import com.example.nikestore.data.repository.cart.CartRepositoryImpl
import com.example.nikestore.data.repository.comment.CommentRepository
import com.example.nikestore.data.repository.comment.CommentRepositoryImpl
import com.example.nikestore.data.repository.order.OrderRemoteDataSource
import com.example.nikestore.data.repository.order.OrderRepository
import com.example.nikestore.data.repository.order.OrderRepositoryImpl
import com.example.nikestore.data.repository.product.ProductRepository
import com.example.nikestore.data.repository.product.ProductRepositoryImpl
import com.example.nikestore.data.repository.source.*
import com.example.nikestore.data.repository.user.UserRepository
import com.example.nikestore.data.repository.user.UserRepositoryImpl
import com.example.nikestore.feature.auth.AuthViewModel
import com.example.nikestore.feature.cart.CartViewModel
import com.example.nikestore.feature.checkout.CheckoutViewModel
import com.example.nikestore.feature.common.ProductListAdapter
import com.example.nikestore.feature.favorites.FavoriteProductsViewModel
import com.example.nikestore.feature.list.ProductListViewModel
import com.example.nikestore.feature.home.HomeViewModel
import com.example.nikestore.feature.main.MainViewModel
import com.example.nikestore.feature.product.ProductDetailViewModel
import com.example.nikestore.feature.product.comment.CommentListViewModel
import com.example.nikestore.feature.profile.ProfileViewModel
import com.example.nikestore.feature.shipping.ShippingViewModel
import com.example.nikestore.modules.FrescoImageLoadingService
import com.example.nikestore.modules.ImageLoadingService
import com.example.nikestore.modules.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
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
            single { Room.databaseBuilder(this@App, AppDatabase::class.java, "db_app").build() }

            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    get<AppDatabase>().productDao()
                )
            }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CartRepository> { CartRepositoryImpl(CartRemoteDataSource(get())) }

            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_settings",
                    MODE_PRIVATE
                )
            }
            single<UserDataSource> { UserLocalDataSource(get()) }
            single<UserRepository> {
                UserRepositoryImpl(
                    UserRemoteDataSource(get()),
                    UserLocalDataSource(get())
                )
            }

            viewModel { HomeViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel { (orderId: Int) -> CheckoutViewModel(orderId, get()) }
            viewModel { ProfileViewModel(get()) }
            viewModel { FavoriteProductsViewModel(get()) }


        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository: UserRepository = get()
        userRepository.loadToken()
    }
}