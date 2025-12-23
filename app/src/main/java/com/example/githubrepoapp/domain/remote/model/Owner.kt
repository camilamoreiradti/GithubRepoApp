package com.example.githubrepoapp.domain.remote.model

import com.google.gson.annotations.SerializedName

data class Owner(
    val profilePhoto: String? = null,
    val id: Int? = null,
    val name: String? = null,
)