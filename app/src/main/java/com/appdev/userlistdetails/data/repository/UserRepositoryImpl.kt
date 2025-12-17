package com.appdev.userlistdetails.data.repository

import com.appdev.userlistdetails.data.local.dao.UserDao
import com.appdev.userlistdetails.data.local.mapper.toDomainFromApi
import com.appdev.userlistdetails.data.local.mapper.toDomainFromLocal
import com.appdev.userlistdetails.data.local.mapper.toEntity
import com.appdev.userlistdetails.data.network.NetworkState
import com.appdev.userlistdetails.data.remote.api.UsersApiService
import com.appdev.userlistdetails.data.utils.ConstantData.DATA_NOT_EXIST
import com.appdev.userlistdetails.data.utils.ConstantData.ERROR_400
import com.appdev.userlistdetails.data.utils.ConstantData.ERROR_500
import com.appdev.userlistdetails.data.utils.ConstantData.UNEXPECTED_ERROR
import com.appdev.userlistdetails.domain.model.User
import com.appdev.userlistdetails.domain.repository.UserRepository
import com.appdev.userlistdetails.domain.utils.StateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UsersApiService,
    private val userDao: UserDao,
    private val networkState: NetworkState
) : UserRepository {

    override fun getUsers(): Flow<StateResult<List<User>>> = flow {

        emit(StateResult.Loading)

        if (networkState.isInternetAvailable()) {

            val response = apiService.getUsers()

            when {
                response.isSuccessful -> {
                    val usersDto = response.body().orEmpty()

                    userDao.insertAll(usersDto.map { it.toEntity() })

                    emit(
                        StateResult.Success(
                            usersDto.map { it.toDomainFromApi() }
                        )
                    )
                }

                response.code() == 400 -> {
                    emit(
                        StateResult.Error(
                            "${response.code()} $ERROR_400"
                        )
                    )
                }

                response.code() == 500 -> {
                    emit(
                        StateResult.Error(
                            "${response.code()} $ERROR_500"
                        )
                    )
                }

                else -> {
                    emit(
                        StateResult.Error(
                            "HTTP ${response.code()}: ${response.message()}"
                        )
                    )
                }
            }

        } else {
            val localData = userDao.getAll().firstOrNull().orEmpty()

            if (localData.isNotEmpty()) {
                emit(
                    StateResult.Success(
                        localData.map { it.toDomainFromLocal() }
                    )
                )
            } else {
                emit(StateResult.Error(DATA_NOT_EXIST))
            }
        }

    }.catch { e ->
        emit(StateResult.Error(e.message ?: UNEXPECTED_ERROR))
    }

    override suspend fun getUserById(id: Int): User? {
        val entity = userDao.getUserById(id)
        return entity?.toDomainFromLocal()
    }
}
