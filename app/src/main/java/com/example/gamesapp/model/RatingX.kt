package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class RatingX(
    @SerializedName("count")
    val count: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("percent")
    val percent: Double,
    @SerializedName("title")
    val title: String
)