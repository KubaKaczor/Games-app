package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class RedditPost(
    @SerializedName("created")
    val created: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("username_url")
    val usernameUrl: String
)