package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        // [?] try e catch estão na implementação, qual a melhor abordagem?
        return accountService.signUp(email, password)
    }
}