package com.example.githubrepoapp.data.remote.mapper

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.domain.remote.model.RepoItem

fun RepoItemResponse.toDomain(): RepoItem {
    return RepoItem(
        description = description,
        id = id,
        name = name,
        owner = owner?.toDomain(),
    )
}