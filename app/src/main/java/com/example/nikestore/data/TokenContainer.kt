package com.example.nikestore.data

import timber.log.Timber

object TokenContainer {

    // this is a private setter to update values by update fun
    var token: String? = null
    private set

    var refreshToken: String? = null
    private set

    fun update(token: String?, refreshToken: String?) {
        Timber.i("Access token -> ${token?.substring(0,10)}  & Refresh tokemn -> ${refreshToken?.substring(0,10)}")
        this.token = token
        this.refreshToken = refreshToken
    }
}