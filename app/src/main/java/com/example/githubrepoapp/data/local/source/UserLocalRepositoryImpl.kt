package com.example.githubrepoapp.data.local.source

import com.example.githubrepoapp.data.local.datastore.UserPreferences
import com.example.githubrepoapp.data.local.datastore.UserPreferencesDataStore
import com.example.githubrepoapp.domain.local.repository.UserLocalRepository
import kotlinx.coroutines.flow.Flow

class UserLocalRepositoryImpl(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserLocalRepository {
    override suspend fun saveUser(userId: String, userEmail: String) {
        userPreferencesDataStore.saveUser(
            userId = userId,
            userEmail = userEmail
        )
    }

    override fun getUser(): Flow<UserPreferences?> {
        return userPreferencesDataStore.userFlow
    }

    override suspend fun clearUser() {
        userPreferencesDataStore.clearUser()
    }
}