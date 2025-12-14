package com.appdev.userlistdetails.ui.screens.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appdev.userlistdetails.domain.usecases.GetUsersUseCase
import com.appdev.userlistdetails.domain.utils.StateResult
import com.appdev.userlistdetails.ui.screens.list.event.UIUserState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersListViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _stateUsers = MutableStateFlow<UIUserState>(UIUserState.Loading)
    val stateUsers: StateFlow<UIUserState> = _stateUsers.asStateFlow()

    fun loadUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            getUsersUseCase().collect { result ->
                when (result) {
                    is StateResult.Loading -> _stateUsers.value = UIUserState.Loading
                    is StateResult.Success -> _stateUsers.value = UIUserState.Success(result.data)
                    is StateResult.Error -> _stateUsers.value = UIUserState.Error(result.message)
                }
            }
        }
    }
}



