package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class ShortScreenshotX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String
)