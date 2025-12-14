package com.appdev.userlistdetails.ui.screens.list.page

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appdev.userlistdetails.ui.screens.list.content.UsersListComponent
import com.appdev.userlistdetails.ui.screens.list.viewmodel.UsersListViewModel

@Composable
fun UsersListScreen(
    viewModel: UsersListViewModel = hiltViewModel(),
    navController: NavController
) {
    UsersListComponent(viewModel, navController)
}