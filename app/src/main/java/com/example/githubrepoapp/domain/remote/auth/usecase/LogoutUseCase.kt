package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke() {
        accountService.logOut()
    }
}