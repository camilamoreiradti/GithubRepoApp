package com.example.githubrepoapp.data.remote.github.mapper

import com.example.githubrepoapp.data.remote.github.model.OwnerResponse
import com.example.githubrepoapp.domain.remote.items.model.Owner

fun OwnerResponse.toDomain(): Owner {
    return Owner(
        profilePhoto = profilePhoto,
        name = name
    )
}