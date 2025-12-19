package com.appdev.userlistdetails.ui.screens.detail.page

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appdev.userlistdetails.ui.screens.detail.content.UserDetailComponent
import com.appdev.userlistdetails.ui.screens.detail.viewmodel.UserDetailViewModel
import com.appdev.userlistdetails.ui.globalevent.AppUiEvent

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
    navController: NavController,
    userId: Int,
    isDarkMode: Boolean,
    onAppEvent: (AppUiEvent) -> Unit,
){
    val stateUserDetail by viewModel.stateUserDetail.collectAsState()

    UserDetailComponent(
        viewModel = viewModel,
        stateUserDetail = stateUserDetail,
        navController = navController,
        userId = userId,
        isDarkMode = isDarkMode,
        onToggleChange = { isDarkMode ->
            onAppEvent(AppUiEvent.ToggleDarkMode(isDarkMode))
        }
    )
}