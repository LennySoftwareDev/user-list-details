package com.appdev.userlistdetails.data.repository

import com.appdev.userlistdetails.data.local.dao.UserDao
import com.appdev.userlistdetails.data.local.mapper.toDomainFromApi
import com.appdev.userlistdetails.data.local.mapper.toDomainFromLocal
import com.appdev.userlistdetails.data.local.mapper.toEntity
import com.appdev.userlistdetails.data.network.NetworkState
import com.appdev.userlistdetails.data.remote.api.UsersApiService
import com.appdev.userlistdetails.data.remote.model.UserDto
import com.appdev.userlistdetails.data.utils.ConstantData.DATA_NOT_EXIST
import com.appdev.userlistdetails.data.utils.ConstantData.ERROR_400
import com.appdev.userlistdetails.data.utils.ConstantData.ERROR_500
import com.appdev.userlistdetails.data.utils.ConstantData.SOCKET_TIMEOUT_EXCEPTION
import com.appdev.userlistdetails.data.utils.ConstantData.UNEXPECTED_ERROR
import com.appdev.userlistdetails.domain.model.User
import com.appdev.userlistdetails.domain.repository.UserRepository
import com.appdev.userlistdetails.domain.utils.StateResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.net.SocketTimeoutException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val apiService: UsersApiService,
    private val userDao: UserDao,
    private val networkState: NetworkState
) : UserRepository {

    private val helper = HelperUserRepository(userDao)

    override fun getUsers(): Flow<StateResult<List<User>>> = flow {

        val helper = HelperUserRepository(userDao)

        emit(StateResult.Loading)

        if (!networkState.isInternetAvailable()) {
            helper.emitLocalDataUsers(this)
            return@flow
        }

        val response = apiService.getUsers()

        if (response.isSuccessful) {
            helper.emitUsersSuccess(this, response)
        } else {
            helper.emitUsersHttpError(this, response)
        }

    }.catch { e ->
        emit(helper.networkException(e))
    }

    override suspend fun getUserById(id: Int): User? {
        val entity = userDao.getUserById(id)
        return entity?.toDomainFromLocal()
    }

    private class HelperUserRepository(private val userDao: UserDao) {

        suspend fun emitLocalDataUsers(flowCollector: FlowCollector<StateResult<List<User>>>) {
            val localData = userDao.getAll().firstOrNull().orEmpty()
            if (localData.isNotEmpty()) {
                flowCollector.emit(StateResult.Success(localData.map { it.toDomainFromLocal() }))
            } else {
                flowCollector.emit(StateResult.Error(DATA_NOT_EXIST))
            }
        }

        suspend fun emitUsersSuccess(
            flowCollector: FlowCollector<StateResult<List<User>>>,
            response: Response<List<UserDto>>
        ) {
            val users = response.body().orEmpty()
            userDao.insertAll(users.map { it.toEntity() })
            flowCollector.emit(StateResult.Success(users.map { it.toDomainFromApi() }))
        }

        suspend fun emitUsersHttpError(
            flowCollector: FlowCollector<StateResult<List<User>>>,
            response: Response<*>
        ) = flowCollector.emit(StateResult.Error(httpError(response)))


        fun httpError(response: Response<*>): String =
            when (response.code()) {
                400 -> ERROR_400
                500 -> ERROR_500
                else -> "HTTP ${response.code()}: ${response.message()}"
            }

        fun networkException(e: Throwable): StateResult.Error =
            when (e) {
                is SocketTimeoutException ->
                    StateResult.Error(SOCKET_TIMEOUT_EXCEPTION)

                else ->
                    StateResult.Error(UNEXPECTED_ERROR)
            }
    }
}
