package com.example.nikestore.data.repository.source

import com.example.nikestore.data.Comment
import com.example.nikestore.modules.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService) : CommentDataSource {

    override fun getAll(productId : Int): Single<List<Comment>> = apiService.getComments(productId)

    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }
}