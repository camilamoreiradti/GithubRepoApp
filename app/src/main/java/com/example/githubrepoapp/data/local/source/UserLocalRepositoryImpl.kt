package com.example.githubrepoapp.data.local.source

import com.example.githubrepoapp.data.local.datastore.UserPreferencesDataStore
import com.example.githubrepoapp.data.local.mapper.toDomain
import com.example.githubrepoapp.domain.local.model.UserLocal
import com.example.githubrepoapp.domain.local.repository.UserLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserLocalRepositoryImpl @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) : UserLocalRepository {
    override suspend fun saveUser(userId: String, userEmail: String?) {
        userPreferencesDataStore.saveUser(
            userId = userId,
            userEmail = userEmail
        )
    }

    override val currentUser: Flow<UserLocal> = userPreferencesDataStore.userFlow
        .map { userPreferences ->
            userPreferences.toDomain()
        }

    override suspend fun clearUser() {
        userPreferencesDataStore.clearUser()
    }
}