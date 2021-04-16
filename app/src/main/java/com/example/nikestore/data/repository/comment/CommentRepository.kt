package com.example.nikestore.data.repository.comment

import com.example.nikestore.data.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId : Int) : Single<List<Comment>>

    fun insert() : Single<Comment>
}