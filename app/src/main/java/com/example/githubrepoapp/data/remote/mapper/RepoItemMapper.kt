package com.example.githubrepoapp.data.remote.mapper

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.domain.remote.model.Owner
import com.example.githubrepoapp.domain.remote.model.RepoItem
import java.util.UUID

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