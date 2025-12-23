package com.example.githubrepoapp.di

import android.content.Context
import com.example.githubrepoapp.BuildConfig
import com.example.githubrepoapp.data.remote.api.GithubAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServiceModule {

    fun providesOkhttpClient(
        context: Context
    ): OkHttpClient {
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

    // configuração da interface service API
    fun providesGithubRepoAPI(
        retrofit: Retrofit
    ): GithubAPI {
        return retrofit.create(GithubAPI::class.java)
    }
}