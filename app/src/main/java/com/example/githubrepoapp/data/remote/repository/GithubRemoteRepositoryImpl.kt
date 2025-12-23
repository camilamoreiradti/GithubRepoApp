package com.example.githubrepoapp.data.remote.repository

import com.example.githubrepoapp.data.remote.api.GithubAPI
import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.data.remote.model.RepoListResponse
import com.example.githubrepoapp.domain.remote.repository.GithubRemoteRepository

class GithubRemoteRepositoryImpl(
    private val githubAPI: GithubAPI
) : GithubRemoteRepository {
    override suspend fun getRepoItem(
        ownerName: String,
        repoName: String
    ): RepoItemResponse {
        return githubAPI.getRepoItem(
            owner = ownerName,
            repo = repoName
        )
    }

    override suspend fun getRepoList(): RepoListResponse {
        return githubAPI.getRepoList()
    }
}