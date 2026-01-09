package com.example.githubrepoapp.data.local.mapper

import com.example.githubrepoapp.data.local.datastore.UserPreferences
import com.example.githubrepoapp.domain.local.model.UserLocal

fun UserPreferences.toDomain(): UserLocal {
    return UserLocal(
        id = userId,
        email = userEmail
    )
}
