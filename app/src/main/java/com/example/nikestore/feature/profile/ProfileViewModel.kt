package com.example.nikestore.feature.profile

import com.example.nikestore.core.NikeViewModel
import com.example.nikestore.data.TokenContainer
import com.example.nikestore.data.repository.user.UserRepository

class ProfileViewModel(private val userRepository: UserRepository) : NikeViewModel() {

    val userName: String
        get() = userRepository.getUsername()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut() = userRepository.signOut()
}