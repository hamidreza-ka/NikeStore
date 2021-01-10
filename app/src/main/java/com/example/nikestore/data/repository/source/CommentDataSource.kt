package com.example.nikestore.data.repository.source

import com.example.nikestore.data.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(productId : Int) : Single<List<Comment>>

    fun insert() : Single<Comment>
}