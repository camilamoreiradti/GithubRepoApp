package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        // [?] try e catch estão na implementação, qual a melhor abordagem?
        return accountService.logIn(email, password)
    }
}