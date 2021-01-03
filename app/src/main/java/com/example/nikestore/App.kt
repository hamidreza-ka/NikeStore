package com.example.nikestore

import android.app.Application
import com.example.nikestore.data.repository.ProductRepository
import com.example.nikestore.data.repository.ProductRepositoryImpl
import com.example.nikestore.data.repository.source.ProductLocalDataSource
import com.example.nikestore.data.repository.source.ProductRemoteDataSource
import com.example.nikestore.feature.main.MainViewModel
import com.example.nikestore.modules.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant()

        val myModules = module {

            single { createApiServiceInstance() }
            factory<ProductRepository> {
                ProductRepositoryImpl(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            viewModel { MainViewModel(get()) }


        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}