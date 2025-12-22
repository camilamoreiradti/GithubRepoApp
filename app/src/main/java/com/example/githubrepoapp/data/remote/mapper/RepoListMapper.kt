package com.example.githubrepoapp.data.remote.mapper

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.data.remote.model.RepoListResponse
import com.example.githubrepoapp.domain.remote.model.RepoItem

fun fromDTOList (dtos: ArrayList<RepoItemResponse>): List<RepoItem> {
    return dtos.map { it.toDomain() }
}