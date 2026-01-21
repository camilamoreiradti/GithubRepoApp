package com.example.githubrepoapp.domain.remote.repositories.usecase

import com.example.githubrepoapp.crashlytics.firebase.FirebaseCrashlyticsService
import com.example.githubrepoapp.domain.remote.repositories.model.RepoItem
import com.example.githubrepoapp.domain.remote.repositories.repository.GithubRemoteRepository
import javax.inject.Inject

class GetRepoListUseCase @Inject constructor(
    private val repository: GithubRemoteRepository,
    private val firebaseCrashlyticsService: FirebaseCrashlyticsService
){
    suspend operator fun invoke(): Result<List<RepoItem>> {
        return try {
            Result.success(repository.getRepoList())
        } catch (e: Exception) {
            val log = mapOf("description" to "retrieve repo item list from firebase")
            firebaseCrashlyticsService.logDataLoadCrash(log, e)
            Result.failure(e)
        }
    }
}