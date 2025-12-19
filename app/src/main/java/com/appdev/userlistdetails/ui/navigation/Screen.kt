package com.appdev.userlistdetails.ui.navigation

sealed class Screen(val route: String) {
    data object UsersListScreen : Screen("users_list_screen")
    data object UserDetailScreen : Screen("user_detail_screen")
}