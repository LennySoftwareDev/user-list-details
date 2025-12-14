package com.appdev.userlistdetails.ui.screens.list.event

import com.appdev.userlistdetails.domain.model.User

sealed class UIUserState {
    object Loading : UIUserState()
    data class Success(
        val users: List<User>
    ): UIUserState()
    data class Error(val message: String) : UIUserState()
}