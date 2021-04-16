package com.example.nikestore.data.repository.user

import io.reactivex.Completable

interface UserRepository {

    fun login(userName: String, password: String): Completable
    fun signUp(userName: String, password: String): Completable
    fun loadToken()
    fun getUsername(): String
    fun signOut()
}