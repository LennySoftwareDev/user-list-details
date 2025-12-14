package com.appdev.userlistdetails.ui.screens.list.content

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appdev.userlistdetails.R
import com.appdev.userlistdetails.ui.components.CardComponent
import com.appdev.userlistdetails.ui.components.CircularProgressComponent
import com.appdev.userlistdetails.ui.components.ErrorComponent
import com.appdev.userlistdetails.ui.components.PullToRefreshComponent
import com.appdev.userlistdetails.ui.components.ScaffoldComponent
import com.appdev.userlistdetails.ui.components.TextComponent
import com.appdev.userlistdetails.ui.navigation.Screen
import com.appdev.userlistdetails.ui.screens.list.event.UIUserState
import com.appdev.userlistdetails.ui.screens.list.viewmodel.UsersListViewModel

@Composable
fun UsersListComponent(
    viewModel: UsersListViewModel = hiltViewModel(),
    navController: NavController
) {
    val activity = LocalActivity.current
    val stateUser = viewModel.stateUsers.collectAsState().value

    LaunchedEffect(true) { viewModel.loadUsers() }

    when (stateUser) {

        is UIUserState.Loading -> {
            CircularProgressComponent()
        }

        is UIUserState.Error -> {
            ErrorComponent(
                message = stateUser.message,
                onRetry = { viewModel.loadUsers()},
                drawable = R.drawable.outline_wifi_tethering_error_24
            )
        }

        is UIUserState.Success -> {
            ScaffoldComponent(
                title = "Users List",
                icon = Icons.AutoMirrored.Filled.ExitToApp,
                onBackClick = { activity?.finish() }
            ) { paddingValues ->

                PullToRefreshComponent(
                    items = stateUser.users,
                    isRefreshing = false,
                    onRefresh = { viewModel.loadUsers() },
                    modifier = Modifier.padding(paddingValues)
                ) { user ->
                    CardComponent(
                        onClick = {
                            navController.navigate(
                                Screen.UserDetailScreen.route.plus("/${user.id}")
                            )
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
                                            append("$label: ")
                                        }
                                        append(value)
                                    },
                                    fontSize = 24.sp,
                                    color = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
