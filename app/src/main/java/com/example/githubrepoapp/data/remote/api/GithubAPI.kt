package com.example.githubrepoapp.data.remote.api

import com.example.githubrepoapp.data.remote.model.RepoItemResponse
import com.example.githubrepoapp.data.remote.model.RepoListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoItem(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ) : RepoItemResponse

    @GET("repositories")
    suspend fun getRepoList() : RepoListResponse
}