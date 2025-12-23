package com.example.githubrepoapp.di

import com.example.githubrepoapp.data.remote.repository.GithubRemoteRepositoryImpl
import com.example.githubrepoapp.domain.remote.repository.GithubRemoteRepository

abstract class AppModule {
    abstract fun bindsRepoRemoteRepositoryImpl(
        repoRemoteRepositoryImpl: GithubRemoteRepositoryImpl
    ) : GithubRemoteRepository
}