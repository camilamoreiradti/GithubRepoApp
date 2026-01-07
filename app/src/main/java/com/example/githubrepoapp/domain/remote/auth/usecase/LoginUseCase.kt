package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser?> {
        // [?] try e catch estão na implementação, qual a melhor abordagem?
        return accountService.logIn(email, password)
    }
}