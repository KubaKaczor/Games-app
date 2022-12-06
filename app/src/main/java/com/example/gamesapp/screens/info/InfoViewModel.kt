package com.example.gamesapp.screens.info

import android.content.ClipData
import androidx.lifecycle.ViewModel
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.BuySite
import com.example.gamesapp.model.Game
import com.example.gamesapp.model.GameDetails
import com.example.gamesapp.model.RedditPost
import com.example.gamesapp.repository.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val gamesRepository: GamesRepository): ViewModel() {
    suspend fun getGameInfo(gameId: String): Resource<com.example.gamesapp.model.GameDetails> {
        return gamesRepository.getBookInfo(gameId)
    }

    suspend fun getGameScreenshots(gameId: String): Resource<List<com.example.gamesapp.model.Result>>{
        return gamesRepository.getGameScreenshots(gameId)
    }

    suspend fun getSitesToBuyGame(gameId: String): Resource<List<BuySite>>{
        return gamesRepository.getSitesToBuyGame(gameId)
    }

    suspend fun getRedditPosts(gameId: String): Resource<List<RedditPost>>{
        return gamesRepository.getRedditPosts(gameId)
    }

    suspend fun getGamesFromSeries(gameId: String): Resource<List<Game>>{
        return gamesRepository.getGamesFromSeries(gameId)
    }
}