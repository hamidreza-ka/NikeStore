package com.example.nikestore.modules.http

import com.example.nikestore.data.TokenContainer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


fun createApiServiceInstance(): ApiService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()

            if (TokenContainer.token != null)
                newRequestBuilder.addHeader("Authorization", "Bearer ${TokenContainer.token}")

            newRequestBuilder.addHeader("Accept", "application/json")
            newRequestBuilder.method(oldRequest.method, oldRequest.body)

            return@addInterceptor it.proceed(newRequestBuilder.build())
        }
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(ApiService::class.java)
}

fun createAnalyticApiServiceInstance(): AnalyticApiService {

    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl("http://130.185.121.239:8088/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(okHttpClient)
        .build()

    return retrofit.create(AnalyticApiService::class.java)
}