package com.example.nikestore.core

import androidx.annotation.StringRes

class NikeExeption(val type: Type, @StringRes val userFriendlyMessage: Int = 0, val serverMessage: String? = null) : Throwable() {

    enum class Type {
        SIMPLE, DIALOG, AUTH
    }
}