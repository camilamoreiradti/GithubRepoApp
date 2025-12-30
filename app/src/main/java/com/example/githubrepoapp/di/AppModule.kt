package com.example.githubrepoapp.di

import com.example.githubrepoapp.data.remote.auth.service.AccountServiceImpl
import com.example.githubrepoapp.data.remote.github.repository.GithubRemoteRepositoryImpl
import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import com.example.githubrepoapp.domain.remote.items.repository.GithubRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

// App (ou domain) Module: responsável por configurar as dependências do repositório
// Interface na camada Domain (o que), implementação na camada data (como)
@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    // Binds é usado para informar a implementação de uma interface
    @Binds
    abstract fun bindsGithubRemoteRepositoryImpl(
        githubRemoteRepositoryImpl: GithubRemoteRepositoryImpl
    ): GithubRemoteRepository

    @Binds
    abstract fun bindsAccountServiceImpl(
        accountServiceImpl: AccountServiceImpl
    ): AccountService
}