package com.example.githubrepoapp.presentation.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import com.example.githubrepoapp.presentation.AuthFormEvent
import com.example.githubrepoapp.presentation.baseviewmodel.State
import com.example.githubrepoapp.presentation.baseviewmodel.UiEvent
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
class SignUpViewModel @Inject constructor(
    private val accountService: AccountService
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<State<User>>(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun loadScreen() {
        _stateFlow.update {
            State.Success(User())
        }
    }

    fun onEvent(event: AuthFormEvent) {
        _stateFlow.update { state ->
            if (state is State.Success) {
                when (event) {
                    is AuthFormEvent.EmailChanged -> {
                        state.copy(data = state.data.copy(email = event.email))
                    }

                    is AuthFormEvent.PasswordChanged -> {
                        state.copy(data = state.data.copy(password = event.password))
                    }

                    is AuthFormEvent.ConfirmPasswordChanged -> {
                        state.copy(data = state.data.copy(confirmPassword = event.confirmPassword))
                    }

                    is AuthFormEvent.Submit -> {
                        onSignUpClick()
                        state
                    }
                }
            } else {
                state
            }
        }
    }

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onSignUpClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _stateFlow.value as State.Success<User>
            val password = currentState.data.password

            var message: String? = null

            when {
                password != currentState.data.confirmPassword -> {
                    message = "Passwords don't match"
                }

                password.length < 6 -> {
                    message = "Password must be at least 6 characters"
                }

                else -> {
                    accountService.signUp(currentState.data.email, currentState.data.password)
                        .fold(
                            onSuccess = { _uiEvent.send(UiEvent.Navigate(ListRoute)) },
                            onFailure = { message = it.message.toString() }
                        )
                }
            }
            message?.let { _uiEvent.send(UiEvent.ShowSnackbar(it)) }
        }
    }
}

data class User(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)