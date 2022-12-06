package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class BuyResult(
    @SerializedName("count")
    val count: Int,
    @SerializedName("next")
    val next: Any,
    @SerializedName("previous")
    val previous: Any,
    @SerializedName("results")
    val results: List<BuySite>
)