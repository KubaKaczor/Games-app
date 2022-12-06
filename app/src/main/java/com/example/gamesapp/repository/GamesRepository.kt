package com.example.gamesapp.repository

import android.content.ClipData
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.*
import com.example.gamesapp.network.GamesApi
import javax.inject.Inject
import kotlin.Result

class GamesRepository @Inject constructor(private val api: GamesApi) {

    suspend fun getUpcomingGames(dates: String): Resource<List<com.example.gamesapp.model.Game>> {
        return try {
            Resource.Loading(data = true)
            val itemList = api.getUpcomingGames(dates).results
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (ex: Exception) {
            Resource.Error(message = ex.message)
        }
    }

    suspend fun getSearchedGames(title: String) : Resource<List<com.example.gamesapp.model.Game>> {
        return try{
            Resource.Loading(data = true)
            val itemList = api.searchGames(title).results
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (ex: Exception){
            Resource.Error(message = ex.message)
        }
    }

    suspend fun getBookInfo(gameId: String): Resource<com.example.gamesapp.model.GameDetails> {
        val response = try {
            Resource.Loading(data = true)
            api.getGameDetails(gameId)

        }catch (exception: Exception){
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }

    suspend fun getGameScreenshots(gameId: String): Resource<List<com.example.gamesapp.model.Result>>{
        return try{
            Resource.Loading(data = true)
            val itemList = api.getGameScreenshots(gameId).results
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (ex: Exception){
            Resource.Error(message = ex.message)
        }
    }

    suspend fun getSitesToBuyGame(gameId: String) : Resource<List<BuySite>>{
        return try{
            Resource.Loading(data = true)
            val itemList = api.getSitesToBuyGame(gameId).results
            if(itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        }catch (e: Exception){
            Resource.Error(message = e.message)
        }
    }

    suspend fun getRedditPosts(gameId: String): Resource<List<RedditPost>>{
        return try{
            Resource.Loading(data = true)
            val items = api.getRedditPosts(id = gameId).results
            if(items.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = items)
        }catch (ex: Exception){
            Resource.Error(message = ex!!.message)
        }
    }

    suspend fun getGamesFromSeries(gameId: String): Resource<List<Game>>{
        return try{
            Resource.Loading(data = true)
            val items = api.getGamesFromSeries(id = gameId).results
            if(items.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = items)
        }catch (ex: Exception){
            Resource.Error(message = ex!!.message)
        }
    }

    suspend fun getGamesByGenre(genreId: String): Resource<List<Game>>{
        return try{
            Resource.Loading(data = true)
            val items = api.getGamesByGenre(genreId = genreId).results
            if(items.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = items)
        }catch (ex: Exception){
            Resource.Error(message = ex!!.message)
        }
    }

    suspend fun getGamesByDev(devId: String): Resource<List<Game>>{
        return try{
            Resource.Loading(data = true)
            val items = api.getGamesByDev(devId = devId).results
            if(items.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = items)
        }catch (ex: Exception){
            Resource.Error(message = ex!!.message)
        }
    }

    suspend fun getGamesByPlatform(platformId: String): Resource<List<Game>>{
        return try{
            Resource.Loading(data = true)
            val items = api.getGamesByPlatform(platformId = platformId).results
            if(items.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = items)
        }catch (ex: Exception){
            Resource.Error(message = ex!!.message)
        }
    }
}