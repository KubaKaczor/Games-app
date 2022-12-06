package com.example.gamesapp.screens.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.Game
import com.example.gamesapp.repository.GamesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val gamesRepository: GamesRepository): ViewModel() {

    var list:  List<com.example.gamesapp.model.Game> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(false)

    fun searchGames(title: String) = viewModelScope.launch {
        isLoading = true
        if(title.isEmpty()) return@launch
        try{
            when(val response = gamesRepository.getSearchedGames(title)){
                is Resource.Success ->{
                    list = response.data!!
                    if(list.isNotEmpty())
                        isLoading = false
                }
                is Resource.Error ->{
                    Log.e("SearchGames", "Error while searching games: ${response.message!!}")
                }
                else ->{}
            }
        }catch (ex: Exception){
            Log.e("SearchGames", "Error while searching games: ${ex.message}")
        }
    }

}