package com.appdev.userlistdetails.data.remote.api

import com.appdev.userlistdetails.data.remote.model.UserDto
import com.appdev.userlistdetails.data.utils.ConstantData.USERS
import retrofit2.Response
import retrofit2.http.GET

interface UsersApiService {
    @GET(USERS)
    suspend fun getUsers(): Response<List<UserDto>>
}