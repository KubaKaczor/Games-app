package com.example.gamesapp.model


import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("added")
    val added: Int,
    @SerializedName("added_by_status")
    val addedByStatus: com.example.gamesapp.model.AddedByStatus,
    @SerializedName("background_image")
    val backgroundImage: String,
    @SerializedName("clip")
    val clip: Any,
    @SerializedName("dominant_color")
    val dominantColor: String,
    @SerializedName("esrb_rating")
    val esrbRating: com.example.gamesapp.model.EsrbRating,
    @SerializedName("genres")
    val genres: List<com.example.gamesapp.model.Genre>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("metacritic")
    val metacritic: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("parent_platforms")
    val parentPlatforms: List<com.example.gamesapp.model.ParentPlatform>,
    @SerializedName("platforms")
    val platforms: List<com.example.gamesapp.model.PlatformX>,
    @SerializedName("playtime")
    val playtime: Int,
    @SerializedName("rating")
    val rating: Double,
    @SerializedName("rating_top")
    val ratingTop: Int,
    @SerializedName("ratings")
    val ratings: List<com.example.gamesapp.model.Rating>,
    @SerializedName("ratings_count")
    val ratingsCount: Int,
    @SerializedName("released")
    val released: String,
    @SerializedName("reviews_count")
    val reviewsCount: Int,
    @SerializedName("reviews_text_count")
    val reviewsTextCount: Int,
    @SerializedName("saturated_color")
    val saturatedColor: String,
    @SerializedName("score")
    val score: Any,
    @SerializedName("short_screenshots")
    val shortScreenshots: List<com.example.gamesapp.model.ShortScreenshot>,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("stores")
    val stores: List<com.example.gamesapp.model.Store>,
    @SerializedName("suggestions_count")
    val suggestionsCount: Int,
    @SerializedName("tags")
    val tags: List<com.example.gamesapp.model.Tag>,
    @SerializedName("tba")
    val tba: Boolean,
    @SerializedName("updated")
    val updated: String,
    @SerializedName("user_game")
    val userGame: Any
)