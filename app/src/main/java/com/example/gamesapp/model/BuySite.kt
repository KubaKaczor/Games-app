package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class BuySite(
    @SerializedName("game_id")
    val gameId: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("store_id")
    val storeId: Int,
    @SerializedName("url")
    val url: String
)