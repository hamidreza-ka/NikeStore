package com.example.nikestore.data.repository

import com.example.nikestore.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanner() : Single<List<Banner>>
}