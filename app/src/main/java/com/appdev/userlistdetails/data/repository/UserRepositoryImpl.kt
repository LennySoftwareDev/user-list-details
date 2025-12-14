package com.appdev.userlistdetails.data.repository

import com.appdev.userlistdetails.data.local.dao.UserDao
import com.appdev.userlistdetails.data.local.mapper.toDomainFromApi
import com.appdev.userlistdetails.data.local.mapper.toDomainFromLocal
import com.appdev.userlistdetails.data.local.mapper.toEntity
import com.appdev.userlistdetails.data.network.NetworkState
import com.appdev.userlistdetails.data.remote.api.ApiException
import com.appdev.userlistdetails.data.remote.api.UsersApiService
import com.appdev.userlistdetails.data.utils.ConstantData.API_ERROR
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

        when (networkState.isInternetAvailable()) {
            true -> {
                try {
                    val usersDto = apiService.getUsers()

                    val roomUsers = usersDto.map { it.toEntity() }

                    val usersFromApi = usersDto.map { it.toDomainFromApi() }

                    userDao.insertAll(roomUsers)

                    emit(StateResult.Success(usersFromApi))

                } catch (e: Exception) {
                    when (e) {
                        is ApiException.ClientError ->
                            emit(
                                StateResult.Error(
                                    "$ERROR_400: ${e.message}"
                                )
                            )

                        is ApiException.ServerError ->
                            emit(
                                StateResult.Error(
                                    "$ERROR_500: ${e.message}"
                                )
                            )

                        else ->
                            emit(
                                StateResult.Error(
                                    e.message ?: API_ERROR
                                )
                            )
                    }
                }
            }

            false -> {
                val localData = userDao.getAll().firstOrNull() ?: emptyList()

                if (localData.isNotEmpty()) {
                    emit(StateResult.Success(localData.map { it.toDomainFromLocal() }))
                } else {
                    emit(StateResult.Error(DATA_NOT_EXIST))
                }
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
