package com.example.githubrepoapp.domain.remote.model

data class RepoItem(
    val description: String? = null,
    val id: Int? = null,
    val name: String? = null,
    val owner: Owner? = null,
)