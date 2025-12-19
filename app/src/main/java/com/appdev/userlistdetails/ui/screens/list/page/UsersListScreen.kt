package com.appdev.userlistdetails.ui.screens.list.page

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appdev.userlistdetails.ui.screens.list.content.UsersListComponent
import com.appdev.userlistdetails.ui.screens.list.viewmodel.UsersListViewModel
import com.appdev.userlistdetails.ui.globalevent.AppUiEvent

@Composable
fun UsersListScreen(
    viewModel: UsersListViewModel = hiltViewModel(),
    navController: NavController,
    isDarkMode: Boolean,
    onAppEvent: (AppUiEvent) -> Unit,
) {

    val context = LocalContext.current
    val activity = context as? Activity

    val stateUser by viewModel.stateUsers.collectAsState()

    UsersListComponent(
        viewModel = viewModel,
        stateUser = stateUser,
        navController = navController,
        isDarkMode = isDarkMode,
        onToggleChange = { isDarkMode ->
            onAppEvent(AppUiEvent.ToggleDarkMode(isDarkMode))
        },
        activity = activity
    )
}