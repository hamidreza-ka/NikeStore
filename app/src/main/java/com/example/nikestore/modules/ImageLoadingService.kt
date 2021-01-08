package com.example.nikestore.modules

import com.example.nikestore.view.NikeImageView

interface ImageLoadingService {

    fun load(imageView : NikeImageView, imageUrl: String)
}