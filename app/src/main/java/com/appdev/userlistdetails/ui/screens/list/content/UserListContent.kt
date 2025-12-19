package com.appdev.userlistdetails.ui.screens.list.content

import android.app.Activity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.appdev.userlistdetails.R
import com.appdev.userlistdetails.ui.components.CardComponent
import com.appdev.userlistdetails.ui.components.CircularProgressComponent
import com.appdev.userlistdetails.ui.components.ErrorComponent
import com.appdev.userlistdetails.ui.components.PullToRefreshComponent
import com.appdev.userlistdetails.ui.components.ScaffoldComponent
import com.appdev.userlistdetails.ui.components.TextComponent
import com.appdev.userlistdetails.ui.components.ThemeToggleComponent
import com.appdev.userlistdetails.ui.screens.list.event.UIListUserEvent
import com.appdev.userlistdetails.ui.screens.list.event.UIListUserState
import com.appdev.userlistdetails.ui.screens.list.viewmodel.UsersListViewModel

@Composable
fun UsersListComponent(
    viewModel: UsersListViewModel,
    stateUser: UIListUserState,
    navController: NavController,
    isDarkMode: Boolean = false,
    onToggleChange: (Boolean) -> Unit = {},
    activity: Activity?
) {

    LaunchedEffect(true) { viewModel.loadUsers() }

    LaunchedEffect(Unit){
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UIListUserEvent.NavigateTo -> navController.navigate(event.route)
                is UIListUserEvent.ExitApp -> activity?.finish()
            }
        }
    }

    when (stateUser) {

        is UIListUserState.Loading -> {
            CircularProgressComponent()
        }

        is UIListUserState.Error -> {
            ErrorComponent(
                message = stateUser.message,
                onRetry = { viewModel.loadUsers()},
                drawable = R.drawable.outline_error_24
            )
        }

        is UIListUserState.Success -> {
            ScaffoldComponent(
                title = "Users List",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                onBackClick = { viewModel.exitApp() },
                actions = {
                    ThemeToggleComponent(
                        isDarkMode = isDarkMode,
                        onToggleChange = onToggleChange
                    )
                }
            ) { paddingValues ->

                PullToRefreshComponent(
                    items = stateUser.users,
                    isRefreshing = false,
                    onRefresh = { viewModel.loadUsers() },
                    modifier = Modifier.padding(paddingValues)
                ) { user ->
                    CardComponent(
                        onClick = {
                            viewModel.navigateToUserDetail(user.id)
                        }
                    ) {
                        Column(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            listOf(
                                "Name: " to user.name,
                                "Email: " to user.email,
                                "City: " to user.city
                            ).forEach { (label, value) ->
                                TextComponent(
                                    text = buildAnnotatedString {
                                        withStyle(SpanStyle(fontWeight = FontWeight.Bold))
                                        {
                                            append("$label ")
                                        }
                                        append(value)
                                    },
                                    fontSize = 24.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
