package com.appdev.userlistdetails.screens.list.viewmodel

import app.cash.turbine.test
import com.appdev.userlistdetails.domain.model.User
import com.appdev.userlistdetails.domain.usecases.GetUsersUseCase
import com.appdev.userlistdetails.domain.utils.StateResult
import com.appdev.userlistdetails.ui.screens.list.event.UIUserState
import com.appdev.userlistdetails.ui.screens.list.viewmodel.UsersListViewModel
import com.appdev.userlistdetails.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.filterNot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class UsersListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUsersUseCase: GetUsersUseCase = mockk()

    private lateinit var viewModel: UsersListViewModel

    @Before
    fun setup() {
        viewModel = UsersListViewModel(getUsersUseCase)
    }

    @Test
    fun `loadUsers should update state with success result`() = runTest {
        val users = listOf(
            User(
                id = 1,
                name = "lenny",
                email = "lenny@test.com",
                city = "Popayán",
                phone = "123",
                website = "developerandroid.com",
                companyName = "appdev"
            )
        )

        coEvery { getUsersUseCase() } returns flowOf(
            StateResult.Success(users)
        )

        viewModel.loadUsers()

        viewModel.stateUsers
            .filterNot { it is UIUserState.Loading }
            .test {

                val success = awaitItem()
                assert(success is UIUserState.Success)
                assertEquals(users, (success as UIUserState.Success).users)

                cancelAndIgnoreRemainingEvents()
            }

    }

    @Test
    fun `loadUsers should return error if use case returns error`() = runTest {
        val errorMessage = "Error obteniendo usuarios"

        coEvery { getUsersUseCase() } returns flowOf(
            StateResult.Error(errorMessage)
        )

        viewModel.loadUsers()

        viewModel.stateUsers.test {

            val errorState = awaitItem()
            assert(errorState is UIUserState.Error)
            assertEquals(errorMessage, (errorState as UIUserState.Error).message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}