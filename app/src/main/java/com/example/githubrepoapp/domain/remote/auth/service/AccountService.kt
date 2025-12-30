package com.example.githubrepoapp.domain.remote.auth.service

import com.example.githubrepoapp.domain.remote.auth.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

interface AccountService {
    val currentUser: Flow<User?>
        get() = callbackFlow {

        }

    val currentUserId: String

    fun hasUser(): Boolean

    suspend fun logIn(email: String, password: String) : Result<Unit>
    suspend fun signUp(email: String, password: String)
    suspend fun logOut()
    suspend fun deleteAccount()
}