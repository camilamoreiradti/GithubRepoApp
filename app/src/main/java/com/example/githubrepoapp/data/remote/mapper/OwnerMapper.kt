package com.example.githubrepoapp.data.remote.mapper

import com.example.githubrepoapp.data.remote.model.OwnerResponse
import com.example.githubrepoapp.domain.remote.model.Owner

fun OwnerResponse.toDomain(): Owner {
    return Owner(
        profilePhoto = profilePhoto,
        id = id,
        name = name
    )
}