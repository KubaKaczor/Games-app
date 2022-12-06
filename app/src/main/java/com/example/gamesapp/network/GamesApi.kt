package com.example.gamesapp.network

import android.content.ClipData
import com.example.gamesapp.BuildConfig
import com.example.gamesapp.model.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface GamesApi {
    @GET("games")
    suspend fun getUpcomingGames(
        @Query("dates")dates: String,
        @Query("ordering")ordering: String = "-added",
        @Query("page_size")pageSize: Int = 20,
        @Query("key")key: String = BuildConfig.API_KEY
    ): Games

    @GET("games")
    suspend fun searchGames(
        @Query("search")search: String,
        @Query("page_size")pageSize: Int = 20,
        @Query("key")key: String = BuildConfig.API_KEY
    ): Games

    @GET("games/{id}")
    suspend fun getGameDetails(
        @Path("id")id: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): GameDetails

    @GET("games/{game_pk}/screenshots")
    suspend fun getGameScreenshots(
        @Path("game_pk")id: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): Screenshots

    @GET("games/{game_pk}/stores")
    suspend fun getSitesToBuyGame(
        @Path("game_pk")id: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): BuyResult

    @GET("games/{id}/reddit")
    suspend fun getRedditPosts(
        @Path("id")id: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): RedditResult

    @GET("games/{game_pk}/game-series")
    suspend fun getGamesFromSeries(
        @Path("game_pk")id: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): SeriesResult

    @GET("games")
    suspend fun getGamesByGenre(
        @Query("genres")genreId: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): Games

    @GET("games")
    suspend fun getGamesByDev(
        @Query("developers")devId: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): Games

    @GET("games")
    suspend fun getGamesByPlatform(
        @Query("platforms")platformId: String,
        @Query("key")key: String = BuildConfig.API_KEY
    ): Games
}