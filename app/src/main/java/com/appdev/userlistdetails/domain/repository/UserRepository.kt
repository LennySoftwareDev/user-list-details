package com.appdev.userlistdetails.domain.repository

import com.appdev.userlistdetails.domain.model.User
import com.appdev.userlistdetails.domain.utils.StateResult
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUsers(): Flow<StateResult<List<User>>>
    suspend fun getUserById(id: Int): User?
}


