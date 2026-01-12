package com.example.githubrepoapp.domain.local.usecase

import com.example.githubrepoapp.domain.local.repository.UserLocalRepository
import javax.inject.Inject

class SaveUserUseCase @Inject constructor(
    private val userLocalRepository: UserLocalRepository
) {
    suspend operator fun invoke(id: String, email: String?) {
        userLocalRepository.saveUser(
            userId = id,
            userEmail = email
        )
    }
}