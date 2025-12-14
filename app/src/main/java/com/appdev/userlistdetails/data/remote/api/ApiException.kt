package com.appdev.userlistdetails.data.remote.api

sealed class ApiException(message: String) : Exception(message) {
    class ClientError(message: String) : ApiException(message)
    class ServerError(message: String) : ApiException(message)
}