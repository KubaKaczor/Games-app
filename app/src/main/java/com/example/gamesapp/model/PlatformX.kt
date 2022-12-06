package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class PlatformX(
    @SerializedName("platform")
    val platform: com.example.gamesapp.model.Platform
)