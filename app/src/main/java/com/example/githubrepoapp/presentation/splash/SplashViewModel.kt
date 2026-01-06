package com.example.githubrepoapp.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.githubrepoapp.domain.remote.auth.usecase.HasUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val hasUserUseCase: HasUserUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Checking)
    val authState = _authState.asStateFlow()

    init {
        onAppStart()
    }

    fun onAppStart() {
        try {
            val isAuthenticated = hasUserUseCase()
            _authState.update {
                if (isAuthenticated) {
                    AuthState.Authenticated
                } else {
                    AuthState.Unauthenticated
                }
            }
        } catch (e: Exception) {
            Log.e("SplashViewModel", "Erro ao verificar auth")
            _authState.update { AuthState.Error(e.message ?: "Unknown error") }
        } finally {
            _isLoading.value = false
        }
    }
}

sealed class AuthState {
    object Checking : AuthState()
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    data class Error(val message: String) : AuthState()
}