package com.appdev.userlistdetails.screens.detail.viewmodel

import com.appdev.userlistdetails.domain.model.User
import com.appdev.userlistdetails.domain.usecases.GetUserByIdUseCase
import com.appdev.userlistdetails.ui.screens.detail.event.UIStateUserDetail
import com.appdev.userlistdetails.ui.screens.detail.viewmodel.UserDetailViewModel
import com.appdev.userlistdetails.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

class UserDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getUserByIdUseCase: GetUserByIdUseCase = mockk()

    private lateinit var viewModel: UserDetailViewModel

    @Before
    fun setup() {
        viewModel = UserDetailViewModel(getUserByIdUseCase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `loadUserDetail should update state with success result`() = runTest {
        val userId = 2
        val user = User(
            id = 2,
            name = "lenny",
            email = "lenny@test.com",
            city = "Popayán",
            phone = "123",
            website = "developerandroid.com",
            companyName = "appdev"
        )

        coEvery { getUserByIdUseCase(userId) } returns user

        viewModel.getUserById(userId)

        val state = viewModel.stateUserDetail.first {
            it is UIStateUserDetail.Content
        }

        assert(state is UIStateUserDetail.Content)

        val content = state as UIStateUserDetail.Content

        assertEquals(user, content.user)

        val expectedDetail = listOf(
            "Name" to "lenny",
            "Email" to "lenny@test.com",
            "Phone" to "123",
            "Website" to "developerandroid.com",
            "City" to "Popayán",
            "Company Name" to "appdev"
        )

        assertEquals(expectedDetail, content.detail)
    }

    @Test
    fun `getUserById should return error if use case returns error or null`() = runTest {
        val errorMessage = "Usuario no encontrado"
        val userId = 2

        coEvery { getUserByIdUseCase(userId) } returns null

        viewModel.getUserById(userId)

        val state = viewModel.stateUserDetail.first {
            it is UIStateUserDetail.Error
        }

        assert(state is UIStateUserDetail.Error)
        assertEquals(errorMessage, (state as UIStateUserDetail.Error).message)
    }
}
