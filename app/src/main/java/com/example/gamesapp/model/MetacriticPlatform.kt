package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class MetacriticPlatform(
    @SerializedName("metascore")
    val metascore: Int,
    @SerializedName("platform")
    val platform: com.example.gamesapp.model.PlatformXX,
    @SerializedName("url")
    val url: String
)