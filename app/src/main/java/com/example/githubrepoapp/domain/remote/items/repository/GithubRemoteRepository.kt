package com.example.githubrepoapp.domain.remote.items.repository

import com.example.githubrepoapp.domain.remote.items.model.RepoItem

interface GithubRemoteRepository {
    suspend fun getRepoItem(ownerName: String, repoName: String) : RepoItem
    suspend fun getRepoList() : List<RepoItem>
}