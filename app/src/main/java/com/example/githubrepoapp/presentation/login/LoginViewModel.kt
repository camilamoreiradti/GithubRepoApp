package com.example.githubrepoapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import com.example.githubrepoapp.presentation.UiEvent
import com.example.githubrepoapp.presentation.navigation.ListRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _stateFlow: MutableStateFlow<StateAuth> = MutableStateFlow(StateAuth.User())
    val stateFlow = _stateFlow.asStateFlow()

    fun onEvent(event: AuthFormEvent) {
        _stateFlow.update { state ->
            (if (state is StateAuth.User) {
                when (event) {
                    is AuthFormEvent.EmailChanged -> {
                        state.copy(email = event.email)
                    }

                    is AuthFormEvent.PasswordChanged -> {
                        state.copy(password = event.password)
                    }

                    is AuthFormEvent.ConfirmPasswordChanged -> {}

                    is AuthFormEvent.Submit -> {
                        onLoginClick()
                        state
                    }
                }
            } else {
                state
            }) as StateAuth
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onLoginClick() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                val currentState = _stateFlow.value as StateAuth.User
                accountService.logIn(currentState.email, currentState.password)
            }.onSuccess {
                _uiEvent.send(UiEvent.Navigate(ListRoute))
            }.onFailure {
                _uiEvent.send(UiEvent.ShowSnackbar("Incorrect email or password"))
            }
        }
    }

    sealed class StateAuth {
        object Error : StateAuth()
        object Loading : StateAuth()
        data class User(
            val email: String = "",
            val password: String = ""
        ) : StateAuth()
    }
}