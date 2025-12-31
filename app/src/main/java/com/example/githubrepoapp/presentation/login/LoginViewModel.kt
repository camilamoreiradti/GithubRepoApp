package com.example.githubrepoapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepoapp.domain.remote.auth.usecase.LoginUseCase
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    private val _stateFlow = MutableStateFlow<State<User>>(State.Loading)
    val stateFlow = _stateFlow.asStateFlow()

    fun loadScreen() {
        _stateFlow.update { _ ->
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
                        state
                    }

                    is AuthFormEvent.Submit -> {
                        onLoginClick()
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

    fun onLoginClick() {
        viewModelScope.launch(Dispatchers.IO) {
            val currentState = _stateFlow.value as State.Success<User>
            val email = currentState.data.email
            val password = currentState.data.password

            var message: String? = null

            when {
                email.isBlank() || password.isBlank() -> {
                    message = "Email and password cannot be empty"
                }

                else -> {
                    loginUseCase(currentState.data.email, currentState.data.password)
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
    val password: String = ""
)