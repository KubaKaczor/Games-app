package com.example.gamesapp.screens.games

import androidx.lifecycle.ViewModel
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.Game
import com.example.gamesapp.model.GameDetails
import com.example.gamesapp.repository.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GamesViewModel @Inject constructor(private val gamesRepository: GamesRepository): ViewModel() {
    suspend fun getGamesByGenre(genreId: String): Resource<List<Game>> {
        return gamesRepository.getGamesByGenre(genreId)
    }

    suspend fun getGamesByDev(devId: String): Resource<List<Game>> {
        return gamesRepository.getGamesByDev(devId)
    }

    suspend fun getGamesByPlatform(platformId: String): Resource<List<Game>> {
        return gamesRepository.getGamesByPlatform(platformId)
    }
}