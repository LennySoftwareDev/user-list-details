package com.appdev.userlistdetails.data.remote.api

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class ErrorInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())

        when (response.code) {
            400 -> throw ApiException.ClientError("Error 400: Bad Request")
            500 -> throw ApiException.ServerError("Error 500: Internal Server Error")
        }

        return response
    }
}