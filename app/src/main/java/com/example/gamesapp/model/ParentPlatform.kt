package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class ParentPlatform(
    @SerializedName("platform")
    val platform: com.example.gamesapp.model.Platform
)