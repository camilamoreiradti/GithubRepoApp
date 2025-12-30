package com.example.githubrepoapp.domain.remote.repositories.usecase

import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.example.githubrepoapp.domain.remote.repositories.repository.GithubRemoteRepository
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(
    private val repository: GithubRemoteRepository
){
    suspend operator fun invoke(): Result<List<RepoItem>> {
        return try {
            Result.success(repository.getRepoList())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}