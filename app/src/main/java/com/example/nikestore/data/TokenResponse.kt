package com.example.nikestore.data

import com.google.gson.annotations.SerializedName

data class TokenResponse(

	@field:SerializedName("access_token")
	val accessToken: String,

	@field:SerializedName("refresh_token")
	val refreshToken: String,

	@field:SerializedName("token_type")
	val tokenType: String,

	@field:SerializedName("expires_in")
	val expiresIn: Int
)
