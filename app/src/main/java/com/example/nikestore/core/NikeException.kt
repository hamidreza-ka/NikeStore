package com.example.nikestore.core

import androidx.annotation.StringRes

class NikeException(val type: Type, @StringRes val userFriendlyMessage: Int = 0, val serverMessage: String? = null) : Throwable() {

    enum class Type {
        SIMPLE, DIALOG, AUTH
    }
}