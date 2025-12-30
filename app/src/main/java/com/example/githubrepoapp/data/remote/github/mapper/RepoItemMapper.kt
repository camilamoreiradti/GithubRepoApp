package com.example.githubrepoapp.data.remote.github.mapper

import com.example.githubrepoapp.data.remote.github.model.RepoItemResponse
import com.example.githubrepoapp.domain.remote.repositories.model.Owner
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem

fun RepoItemResponse.toDomain(): RepoItem {
    return RepoItem(
        description = description  ?: "No description",
        name = name ?: "No name found",
        fullName = fullName ?: "No name found",
        owner = owner?.toDomain() ?: Owner(
            profilePhoto = "",
            name = ""
        ),
    )
}