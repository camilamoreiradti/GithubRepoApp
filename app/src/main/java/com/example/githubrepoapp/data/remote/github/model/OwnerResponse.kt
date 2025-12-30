package com.example.githubrepoapp.data.remote.github.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class OwnerResponse(

    @SerializedName("login")
    val name: String,

    // serialize custom name to api name
    @SerializedName("avatar_url")
    val profilePhoto: String,
)