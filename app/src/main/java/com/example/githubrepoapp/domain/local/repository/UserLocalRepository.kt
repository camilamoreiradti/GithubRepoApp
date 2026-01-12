package com.example.githubrepoapp.domain.local.repository

import com.example.githubrepoapp.domain.local.model.UserLocal
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    suspend fun saveUser(userId: String, userEmail: String?)
    val currentUser: Flow<UserLocal>
    suspend fun clearUser()
}