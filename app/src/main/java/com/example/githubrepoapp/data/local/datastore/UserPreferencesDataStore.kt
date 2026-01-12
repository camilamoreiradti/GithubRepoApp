package com.example.githubrepoapp.data.local.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

// instancia datastore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user_preferences")

class UserPreferencesDataStore(
    private val context: Context
) {

    // salvar dados
    suspend fun saveUser(
        userId: String, userEmail: String?
    ) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.USER_ID] = userId
            preferences[PreferencesKeys.USER_EMAIL] = userEmail ?: ""
        }
    }

    // ler dados com flow
    val userFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->

            // insert condição se estiver logado, else retorna null

            UserPreferences(
                userId = preferences[PreferencesKeys.USER_ID] ?: "No ID found",
                userEmail = preferences[PreferencesKeys.USER_EMAIL] ?: "No email found"
            )
        }

    // limpar dados no logout
    suspend fun clearUser() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}

data class UserPreferences(
    val userId: String,
    val userEmail: String
)