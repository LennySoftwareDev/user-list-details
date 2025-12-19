package com.appdev.userlistdetails.ui.screens.detail.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdev.userlistdetails.domain.usecases.GetUserByIdUseCase
import com.appdev.userlistdetails.ui.screens.detail.event.UIUserDetailState
import com.appdev.userlistdetails.ui.screens.detail.event.UIUserDetailEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UIUserDetailEvent>()
    val uiEvent: SharedFlow<UIUserDetailEvent> = _uiEvent

    private val _stateUserDetail = MutableStateFlow<UIUserDetailState>(UIUserDetailState.Loading)
    val stateUserDetail: StateFlow<UIUserDetailState> = _stateUserDetail.asStateFlow()

    fun getUserById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _stateUserDetail.value = UIUserDetailState.Loading

            val user = getUserByIdUseCase(id)
            if (user != null) {
                _stateUserDetail.value = UIUserDetailState.Content(
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
                _stateUserDetail.value = UIUserDetailState.Error("Usuario no encontrado")
            }
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _uiEvent.emit(UIUserDetailEvent.NavigateBack)
        }
    }
}
