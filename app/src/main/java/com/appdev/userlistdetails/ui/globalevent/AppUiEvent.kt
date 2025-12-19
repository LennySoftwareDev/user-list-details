package com.appdev.userlistdetails.ui.globalevent

sealed class AppUiEvent {
    data class ToggleDarkMode(val enabled: Boolean) : AppUiEvent()
}