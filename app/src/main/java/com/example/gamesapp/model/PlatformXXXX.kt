package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class PlatformXXXX(
    @SerializedName("platform")
    val platform: com.example.gamesapp.model.PlatformXXXXX,
    @SerializedName("released_at")
    val releasedAt: String,
    @SerializedName("requirements")
    val requirements: com.example.gamesapp.model.Requirements
)