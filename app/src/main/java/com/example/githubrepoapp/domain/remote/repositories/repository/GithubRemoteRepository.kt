package com.example.githubrepoapp.domain.remote.repositories.repository

import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem

interface GithubRemoteRepository {
    suspend fun getRepoItem(ownerName: String, repoName: String) : RepoItem
    suspend fun getRepoList() : List<RepoItem>
}