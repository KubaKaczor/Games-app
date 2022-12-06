package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class StoreXX(
    @SerializedName("id")
    val id: Int,
    @SerializedName("store")
    val store: com.example.gamesapp.model.StoreXXX,
    @SerializedName("url")
    val url: String
)