package com.example.githubrepoapp.domain.remote.auth.usecase

import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import javax.inject.Inject

class HasUserUseCase @Inject constructor(
    private val accountService: AccountService
) {
    operator fun invoke() : Boolean {
        return accountService.hasUser()
    }
}