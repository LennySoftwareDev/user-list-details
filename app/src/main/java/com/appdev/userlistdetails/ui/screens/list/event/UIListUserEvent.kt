package com.appdev.userlistdetails.ui.screens.list.event

sealed interface UIListUserEvent {
    data class NavigateTo(val route: String) : UIListUserEvent
    object ExitApp : UIListUserEvent
}


