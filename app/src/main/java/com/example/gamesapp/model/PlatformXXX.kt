package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class PlatformXXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String
)