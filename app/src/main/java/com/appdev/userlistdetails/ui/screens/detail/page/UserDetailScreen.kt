package com.appdev.userlistdetails.ui.screens.detail.page

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.appdev.userlistdetails.ui.screens.detail.content.UserDetailComponent
import com.appdev.userlistdetails.ui.screens.detail.viewmodel.UserDetailViewModel

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel = hiltViewModel(),
    navController: NavController,
    userId: Int
){
    UserDetailComponent(viewModel,navController,userId)
}