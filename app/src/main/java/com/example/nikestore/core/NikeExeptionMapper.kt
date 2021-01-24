package com.example.nikestore.core

import com.example.nikestore.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.lang.Exception

class NikeExeptionMapper {

    companion object {

        fun map(throwable: Throwable): NikeExeption {

            if (throwable is HttpException) {

                try {
                    val errorJsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJsonObject.getString("message")
                    return NikeExeption(
                        if (throwable.code() == 401) NikeExeption.Type.AUTH else NikeExeption.Type.SIMPLE,
                        serverMessage = errorMessage
                    )
                } catch (exception: Exception) {
                    Timber.e(exception)
                }

            }

            return NikeExeption(NikeExeption.Type.SIMPLE, R.string.unknownError)

        }
    }

}