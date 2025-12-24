package com.example.githubrepoapp.domain.remote.repository

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.domain.remote.model.RepoItem

interface GithubRemoteRepository {
    suspend fun getRepoItem(ownerName: String, repoName: String) : RepoItem
    suspend fun getRepoList() : List<RepoItem>
}