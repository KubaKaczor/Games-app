package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class EsrbRatingX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("slug")
    val slug: String
)