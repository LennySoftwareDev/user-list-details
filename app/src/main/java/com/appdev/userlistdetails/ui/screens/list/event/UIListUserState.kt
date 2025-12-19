package com.appdev.userlistdetails.ui.screens.list.event

import com.appdev.userlistdetails.domain.model.User

sealed class UIListUserState {
    object Loading : UIListUserState()
    data class Success(
        val users: List<User>
    ): UIListUserState()
    data class Error(val message: String) : UIListUserState()
}