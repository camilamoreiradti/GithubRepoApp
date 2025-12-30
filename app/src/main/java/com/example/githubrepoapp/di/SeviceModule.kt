package com.example.githubrepoapp.di

import android.content.Context
import com.example.githubrepoapp.BuildConfig
import com.example.githubrepoapp.data.remote.auth.service.AccountServiceImpl
import com.example.githubrepoapp.domain.remote.auth.service.AccountService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Service (ou Network) Module: reponsável por configurar as dependências de classes da camada Service/Network
@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    // @Provides é usado para implementações de classes que não são suas (biblioteca externa hilt, retrofit, room etc)
    // ou se as intâncias precisão ser criandas com padrão build
    @Provides
    fun providesOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            // tempo que ele pode levar para se conectar com a API
            .connectTimeout(30, TimeUnit.SECONDS)

            .readTimeout(30, TimeUnit.SECONDS)

            // visualiza logs das requisições
            .addInterceptor(HttpLoggingInterceptor().apply {
                // só aparece os logs no modo debug
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            })
            .build()
    }

    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            // converte o retorno Json para o objeto
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }
}