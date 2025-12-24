package com.example.githubrepoapp.domain.usecase

import com.example.githubrepoapp.domain.remote.model.RepoItem
import com.example.githubrepoapp.domain.remote.repository.GithubRemoteRepository
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