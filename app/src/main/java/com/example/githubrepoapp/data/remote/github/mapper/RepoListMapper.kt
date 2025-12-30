package com.example.githubrepoapp.data.remote.github.mapper

import com.example.githubrepoapp.data.remote.github.model.RepoItemResponse
import com.example.githubrepoapp.domain.remote.items.model.RepoItem

fun fromDTOList (dtos: List<RepoItemResponse>): List<RepoItem> {
    return dtos.map { it.toDomain() }
}