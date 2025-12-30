package com.example.githubrepoapp.di

import com.example.githubrepoapp.data.remote.github.service.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

// DataModule: responsável por criar as injeções dos arquivos de API
@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    // configuração da interface service API

    // @Provides é usado para implementações de classes que não são suas (biblioteca externa hilt, retrofit, room etc)
    // ou se as intâncias precisam ser criadas com padrão build
    @Provides
    fun providesGithubRepoAPI(
        retrofit: Retrofit
    ): GithubService {
        return retrofit.create(GithubService::class.java)
    }
}