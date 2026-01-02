package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService

class LogoutUseCase(
    private val accountService: AccountService
) {
    suspend operator fun invoke() {
        accountService.logOut()
    }
}