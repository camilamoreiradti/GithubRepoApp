package com.example.githubrepoapp.data.remote.mapper

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.domain.remote.model.RepoItem

fun fromDTOList (dtos: List<RepoItemResponse>): List<RepoItem> {
    return dtos.map { it.toDomain() }
}