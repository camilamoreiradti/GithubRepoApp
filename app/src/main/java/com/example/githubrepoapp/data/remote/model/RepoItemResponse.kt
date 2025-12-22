package com.example.githubrepoapp.data.remote.model

data class RepoItemResponse(
    val description: String?,
    val id: Int?,
    val name: String?,
    val owner: OwnerResponse?,
)