package com.example.githubrepoapp.domain.local.usecase

import com.example.githubrepoapp.domain.local.model.UserLocal
import com.example.githubrepoapp.domain.local.repository.UserLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val userLocalRepository: UserLocalRepository
) {
     operator fun invoke() : Flow<UserLocal> {
        return userLocalRepository.currentUser
    }
}