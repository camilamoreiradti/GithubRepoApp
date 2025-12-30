package com.example.githubrepoapp.domain.remote.repositories.usecase

import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.example.githubrepoapp.domain.remote.repositories.repository.GithubRemoteRepository
import javax.inject.Inject

class GetRepoItemUseCase @Inject constructor(
    private val repository: GithubRemoteRepository
) {
    suspend operator fun invoke(ownerName: String, repoName: String): Result<RepoItem> {
        return try {
            Result.success(repository.getRepoItem(ownerName = ownerName, repoName = repoName))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}