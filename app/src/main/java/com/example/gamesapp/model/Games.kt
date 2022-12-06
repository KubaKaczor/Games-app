package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class Games(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: String,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<com.example.gamesapp.model.Game>,
    @SerializedName("user_platforms")
    val userPlatforms: Boolean
)