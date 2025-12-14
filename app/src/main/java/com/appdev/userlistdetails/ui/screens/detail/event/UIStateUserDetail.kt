package com.appdev.userlistdetails.ui.screens.detail.event

import com.appdev.userlistdetails.domain.model.User

sealed class UIStateUserDetail {
    object Loading : UIStateUserDetail()
    data class Content(
        val user: User,
        val detail: List<Pair<String,String>>
    ) : UIStateUserDetail()
    data class Error(val message: String) : UIStateUserDetail()
}
