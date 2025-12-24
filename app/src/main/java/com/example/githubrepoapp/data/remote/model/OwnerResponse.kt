package com.example.githubrepoapp.data.remote.model

import com.google.gson.annotations.SerializedName

data class OwnerResponse(
    val id: Int,

    @SerializedName("login")
    val name: String,

    // serialize custom name to api name
    @SerializedName("avatar_url")
    val profilePhoto: String,
)