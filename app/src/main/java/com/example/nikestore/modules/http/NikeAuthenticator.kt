package com.example.nikestore.modules.http

import com.example.nikestore.data.TokenContainer
import com.example.nikestore.data.TokenResponse
import com.example.nikestore.data.repository.source.CLIENT_ID
import com.example.nikestore.data.repository.source.CLIENT_SECRET
import com.example.nikestore.data.repository.source.UserLocalDataSource
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber
import java.lang.Exception

class NikeAuthenticator : Authenticator, KoinComponent {

    private val apiService : ApiService by inject()
    private val userLocalDataSource : UserLocalDataSource by inject()

    override fun authenticate(route: Route?, response: Response): Request? {
       if (TokenContainer.token != null && TokenContainer.refreshToken != null && response.request.url.pathSegments.last() != "token"){

           try {
               val token = refreshToken()
               if (token.isEmpty())
                   return null

               return response.request.newBuilder()
                   .header("Authorization", "Bearer $token").build()

           }catch (exception: Exception){
               Timber.e(exception)
           }
       }

        return null
    }


    private fun refreshToken() : String{

        val response : retrofit2.Response<TokenResponse> = apiService.refreshToken(JsonObject().apply {
            addProperty("grant_type", "refresh_token")
            addProperty("refresh_token", TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        }).execute()

        response.body()?.let {
            TokenContainer.update(it.accessToken, it.refreshToken)
            userLocalDataSource.saveToken(it.accessToken, it.refreshToken)
            return it.accessToken
        }

        return ""
    }
}