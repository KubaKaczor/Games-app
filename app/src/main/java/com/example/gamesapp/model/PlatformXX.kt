package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class PlatformXX(
    @SerializedName("name")
    val name: String,
    @SerializedName("platform")
    val platform: Int,
    @SerializedName("slug")
    val slug: String
)