package com.example.nikestore.feature.auth

import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.repository.UserRepository
import io.reactivex.Completable

class AuthViewModel(private val userRepository: UserRepository) : NikeViewModel() {

    fun login(email: String, password: String): Completable {
        progressLiveData.value = true
        return userRepository.login(email, password)
            .doFinally { progressLiveData.postValue(false) }
    }

    fun signUp(email: String, password: String): Completable {
        progressLiveData.value = true
        return userRepository.signUp(email, password)
            .doFinally { progressLiveData.value = false }
    }
}