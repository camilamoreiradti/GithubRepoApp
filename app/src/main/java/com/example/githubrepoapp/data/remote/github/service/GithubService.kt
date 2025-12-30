package com.example.githubrepoapp.data.remote.github.service

import com.example.githubrepoapp.data.remote.github.model.RepoItemResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("repos/{owner}/{repo}")
    suspend fun getRepoItem(
        @Path("owner") owner: String,
        @Path("repo") repo: String
    ) : RepoItemResponse

    @GET("repositories")
    suspend fun getRepoList(
        @Query("per_page") perPage: Int = 15
    ) : List<RepoItemResponse>
}