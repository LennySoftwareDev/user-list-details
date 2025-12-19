package com.appdev.userlistdetails.ui.screens.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdev.userlistdetails.domain.usecases.GetUsersUseCase
import com.appdev.userlistdetails.domain.utils.StateResult
import com.appdev.userlistdetails.ui.navigation.Screen
import com.appdev.userlistdetails.ui.screens.list.event.UIListUserEvent
import com.appdev.userlistdetails.ui.screens.list.event.UIListUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<UIListUserEvent>()
    val uiEvent: SharedFlow<UIListUserEvent> = _uiEvent.asSharedFlow()

    private val _stateUsers = MutableStateFlow<UIListUserState>(UIListUserState.Loading)
    val stateUsers: StateFlow<UIListUserState> = _stateUsers.asStateFlow()

    fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUseCase().collect { result ->
                when (result) {
                    is StateResult.Loading -> _stateUsers.value = UIListUserState.Loading
                    is StateResult.Success -> _stateUsers.value =
                        UIListUserState.Success(result.data)

                    is StateResult.Error -> _stateUsers.value =
                        UIListUserState.Error(result.message)
                }
            }
        }
    }

    fun navigateToUserDetail(userId: Int) =
        viewModelScope.launch {
            _uiEvent.emit(
                UIListUserEvent.NavigateTo(
                    Screen.UserDetailScreen.route.plus("/$userId")
                )
            )
        }

    fun exitApp() = viewModelScope.launch {
        _uiEvent.emit(UIListUserEvent.ExitApp)
    }
}



