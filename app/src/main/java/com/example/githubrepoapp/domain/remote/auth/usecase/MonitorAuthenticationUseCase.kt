package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.model.User
import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MonitorAuthenticationUseCase @Inject constructor(
    private val accountService: AccountService
) {
    operator fun invoke(): Flow<AuthState> {
        return accountService.currentUser.map { user ->
            when {
                user == null -> AuthState.Unauthenticated
                else -> AuthState.Authenticated(user)
            }
        }
    }
}

sealed interface AuthState {
    data object Unauthenticated : AuthState
    data class Authenticated(
        val user: User
    ) : AuthState
}