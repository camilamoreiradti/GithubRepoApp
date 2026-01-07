package com.example.githubrepoapp.domain.local.repository

import com.example.githubrepoapp.data.local.datastore.UserPreferences
import kotlinx.coroutines.flow.Flow

interface UserLocalRepository {
    suspend fun saveUser(userId: String, userEmail: String)
    fun getUser(): Flow<UserPreferences?>
    suspend fun clearUser()
}