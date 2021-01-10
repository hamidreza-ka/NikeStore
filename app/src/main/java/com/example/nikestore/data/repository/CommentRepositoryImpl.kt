package com.example.nikestore.data.repository

import com.example.nikestore.data.Comment
import com.example.nikestore.data.repository.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImpl(val commentDataSource: CommentDataSource) : CommentRepository {

    override fun getAll(productId : Int): Single<List<Comment>> = commentDataSource.getAll(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}