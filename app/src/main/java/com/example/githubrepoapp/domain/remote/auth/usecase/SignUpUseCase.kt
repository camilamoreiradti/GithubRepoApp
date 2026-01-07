package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val accountService: AccountService
) {
    suspend operator fun invoke(email: String, password: String): Result<FirebaseUser?> {
        // [?] try e catch estão na implementação, qual a melhor abordagem?
        //      > tratando o erro na implementação evita de ter que repetir o tratamento em outros usecases que usarem a função
        return accountService.signUp(email, password)
    }
}