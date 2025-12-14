package com.appdev.userlistdetails.domain.usecases

import com.appdev.userlistdetails.domain.repository.UserRepository
import javax.inject.Inject

class GetUserByIdUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(id: Int) = repository.getUserById(id)
}