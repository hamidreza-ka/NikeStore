package com.example.nikestore.data.repository.source

import com.example.nikestore.data.MessageResponse
import com.example.nikestore.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(userName: String, password: String): Single<TokenResponse>
    fun signUp(userName: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)
    fun saveUserName(userName: String)
    fun getUserName(): String
    fun signOut()
}