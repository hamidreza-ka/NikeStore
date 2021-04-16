package com.example.nikestore.feature.product.comment

import androidx.lifecycle.MutableLiveData
import com.example.nikestore.core.NikeSingleObserver
import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.core.asyncNetworkRequest
import com.example.nikestore.data.Comment
import com.example.nikestore.data.repository.comment.CommentRepository

class CommentListViewModel(productId: Int, commentRepository: CommentRepository) : NikeViewModel() {

    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {

        progressLiveData.value = true
        commentRepository.getAll(productId)
            .asyncNetworkRequest()
            .doFinally { progressLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {

                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

            })
    }
}