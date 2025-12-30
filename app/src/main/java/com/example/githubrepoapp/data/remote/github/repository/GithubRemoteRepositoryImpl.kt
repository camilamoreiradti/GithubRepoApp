package com.example.githubrepoapp.data.remote.github.repository

import com.example.githubrepoapp.data.remote.github.mapper.fromDTOList
import com.example.githubrepoapp.data.remote.github.mapper.toDomain
import com.example.githubrepoapp.data.remote.github.service.GithubService
import com.example.githubrepoapp.domain.remote.items.model.RepoItem
import com.example.githubrepoapp.domain.remote.items.repository.GithubRemoteRepository
import javax.inject.Inject

class GithubRemoteRepositoryImpl @Inject constructor(
    private val githubService: GithubService
) : GithubRemoteRepository {
    override suspend fun getRepoItem(
        ownerName: String,
        repoName: String
    ): RepoItem {
        return githubService.getRepoItem(
            owner = ownerName,
            repo = repoName
        ).toDomain()
    }

    override suspend fun getRepoList(): List<RepoItem> {
        return fromDTOList(githubService.getRepoList())
    }
}