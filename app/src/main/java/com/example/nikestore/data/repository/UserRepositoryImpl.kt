package com.example.nikestore.data.repository

import com.example.nikestore.data.TokenContainer
import com.example.nikestore.data.TokenResponse
import com.example.nikestore.data.repository.source.UserDataSource
import io.reactivex.Completable

class UserRepositoryImpl(
    private val userRemoteDataSource: UserDataSource,
    private val userLocalDataSource: UserDataSource
) : UserRepository {

    override fun login(userName: String, password: String): Completable {
        return userRemoteDataSource.login(userName, password).doOnSuccess {
            onSuccessfulLogin(it)
        }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {

        return userRemoteDataSource.signUp(userName, password).flatMap {
            userRemoteDataSource.login(userName, password)
        }
            .doOnSuccess {
            onSuccessfulLogin(it)
        }
            .ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    private fun onSuccessfulLogin(tokenResponse: TokenResponse) {
        TokenContainer.update(tokenResponse.accessToken, tokenResponse.refreshToken)
        userLocalDataSource.saveToken(tokenResponse.accessToken, tokenResponse.refreshToken)
    }
}
