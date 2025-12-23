package com.example.githubrepoapp.domain.remote.repository

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.data.remote.model.RepoListResponse

interface GithubRemoteRepository {
    suspend fun getRepoItem(ownerName: String, repoName: String) : RepoItemResponse
    suspend fun getRepoList() : RepoListResponse
}