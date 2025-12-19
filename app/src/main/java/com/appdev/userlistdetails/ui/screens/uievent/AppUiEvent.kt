package com.appdev.userlistdetails.ui.screens.uievent

sealed class AppUiEvent {
    data class ToggleDarkMode(val enabled: Boolean) : AppUiEvent()
}