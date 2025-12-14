package com.appdev.userlistdetails.ui.screens.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdev.userlistdetails.domain.usecases.GetUserByIdUseCase
import com.appdev.userlistdetails.ui.screens.detail.event.UIStateUserDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _stateUserDetail = MutableStateFlow<UIStateUserDetail>(UIStateUserDetail.Loading)
    val stateUserDetail: StateFlow<UIStateUserDetail> = _stateUserDetail.asStateFlow()

    fun getUserById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateUserDetail.value = UIStateUserDetail.Loading

            val user = getUserByIdUseCase(id)
            if (user != null) {
                _stateUserDetail.value = UIStateUserDetail.Content(
                    user = user,
                    detail = listOf(
                        "Name" to user.name,
                        "Email" to user.email,
                        "Phone" to user.phone,
                        "Website" to user.website,
                        "City" to user.city,
                        "Company Name" to user.companyName
                    )
                )
            } else {
                _stateUserDetail.value = UIStateUserDetail.Error("Usuario no encontrado")
            }
        }
    }
}
