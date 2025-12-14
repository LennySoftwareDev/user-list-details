package com.appdev.userlistdetails.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.appdev.userlistdetails.ui.navigation.NavigationRoutes.USERS_LIST_SCREEN
import com.appdev.userlistdetails.ui.navigation.NavigationRoutes.USER_DETAIL_SCREEN
import com.appdev.userlistdetails.ui.screens.detail.page.UserDetailScreen
import com.appdev.userlistdetails.ui.screens.list.page.UsersListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = USERS_LIST_SCREEN
    ) {
        composable(
            route = USERS_LIST_SCREEN,
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(durationMillis = 500)
                )
            }
        ) {
            UsersListScreen(navController = navController)
        }
        composable(
            route = USER_DETAIL_SCREEN,
            arguments = listOf(navArgument("userId"){type = NavType.IntType}),
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(durationMillis = 500)
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { it },
                    animationSpec = tween(durationMillis = 500)
                )
            }
        ) { navBackStackEntry ->
            val id = navBackStackEntry.arguments?.getInt("userId") ?: 0

            UserDetailScreen(navController = navController, userId = id)
        }
    }
}