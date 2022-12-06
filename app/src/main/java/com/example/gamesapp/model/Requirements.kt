package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class Requirements(
    @SerializedName("minimum")
    val minimum: String,
    @SerializedName("recommended")
    val recommended: String
)