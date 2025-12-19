package com.appdev.userlistdetails.ui.screens.detail.event

import com.appdev.userlistdetails.domain.model.User

sealed class UIUserDetailState {
    object Loading : UIUserDetailState()
    data class Content(
        val user: User,
        val detail: List<Pair<String, String>>
    ) : UIUserDetailState()
    data class Error(val message: String) : UIUserDetailState()
}
