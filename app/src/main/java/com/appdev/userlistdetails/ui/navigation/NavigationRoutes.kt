package com.appdev.userlistdetails.ui.navigation

object NavigationRoutes {

    val USERS_LIST_SCREEN = Screen.UsersListScreen.route
    val USER_DETAIL_SCREEN = Screen.UserDetailScreen.route.plus("/{userId}")
}