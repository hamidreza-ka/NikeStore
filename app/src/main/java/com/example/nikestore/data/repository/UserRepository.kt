package com.example.nikestore.data.repository

import io.reactivex.Completable

interface UserRepository {

    fun login(userName : String, password: String) : Completable
    fun signUp(userName : String, password: String) : Completable
    fun loadToken()
}