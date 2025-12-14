package com.appdev.userlistdetails.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.appdev.userlistdetails.ui.navigation.AppNavigation
import com.appdev.userlistdetails.ui.theme.UserListDetailsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UserListDetailsTheme {
                AppNavigation()
            }
        }
    }
}