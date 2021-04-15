package com.example.nikestore.data.repository

import com.example.nikestore.data.Banner
import com.example.nikestore.data.repository.source.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImpl(private val bannerDataSource: BannerDataSource) : BannerRepository {

    override fun getBanner(): Single<List<Banner>> = bannerDataSource.getBanner()
}