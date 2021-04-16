package com.example.nikestore.data.repository.banner

import com.example.nikestore.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanner() : Single<List<Banner>>
}