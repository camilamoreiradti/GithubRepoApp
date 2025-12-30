package com.example.githubrepoapp.data.remote.github.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RepoItemResponse(
    val description: String? = null,
    val name: String? = null,
    @SerializedName("full_name")
    val fullName: String? = null,
    val owner: OwnerResponse? = null,
)