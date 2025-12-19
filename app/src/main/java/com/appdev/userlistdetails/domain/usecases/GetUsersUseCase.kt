package com.appdev.userlistdetails.domain.usecases

import com.appdev.userlistdetails.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    operator fun invoke() = repository.getUsers()
}


