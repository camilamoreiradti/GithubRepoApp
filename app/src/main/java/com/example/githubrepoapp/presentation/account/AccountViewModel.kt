package com.example.githubrepoapp.presentation.account

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.data.local.datastore.UserPreferences
import com.example.githubrepoapp.domain.local.model.UserLocal
import com.example.githubrepoapp.domain.local.usecase.GetUserUseCase
import com.example.githubrepoapp.domain.remote.auth.usecase.LogoutUseCase
import com.example.githubrepoapp.presentation.baseviewmodel.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    getUserUseCase: GetUserUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<State<UserLocal>> = MutableStateFlow(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    val currentUser: Flow<UserLocal> = getUserUseCase()

    init {
        viewModelScope.launch {
            currentUser.collect { userLocal ->
                _stateFlow.update { State.Success(userLocal) }
            }
        }
    }

    fun onLogoutClick(toLogIn: () -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase()
            toLogIn()
        }
    }

}