package com.example.githubrepoapp.domain.usecase

import com.example.githubrepoapp.domain.remote.model.RepoItem
import com.example.githubrepoapp.domain.remote.repository.GithubRemoteRepository
import javax.inject.Inject

class GetRepoItemUseCase @Inject constructor(
    private val repository: GithubRemoteRepository
) {
    suspend operator fun invoke(ownerName: String, repoName: String): RepoItem {
        return repository.getRepoItem(ownerName = ownerName, repoName = repoName)
    }
}