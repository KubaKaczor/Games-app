package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("height")
    val height: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("is_deleted")
    val isDeleted: Boolean,
    @SerializedName("width")
    val width: Int
)