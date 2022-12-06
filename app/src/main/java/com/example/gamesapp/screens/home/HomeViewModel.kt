package com.example.gamesapp.screens.home

import android.content.ClipData
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.Game
import com.example.gamesapp.repository.GamesRepository
import com.example.gamesapp.utils.getPeriodDates
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val gamesRepository: GamesRepository): ViewModel() {

    var list: List<com.example.gamesapp.model.Game> by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(false)

    init {
        val periodsDates = getPeriodDates(6.toLong())
        getUpcomingBooks(periodsDates)
    }

    fun getUpcomingBooks(dates: String) = viewModelScope.launch {
        isLoading = true
        if(dates.isEmpty()) return@launch
        try{
            when(val response = gamesRepository.getUpcomingGames(dates)){
                is Resource.Success ->{
                    list = response.data!!.sortedBy {
                        it.released
                    }
                    if(list.isNotEmpty())
                        isLoading = false
                }
                is Resource.Error ->{
                    Log.e("Network", "Search games failed. ${response.message!!}")
                }
                else -> {}
            }

        }catch (ex: Exception){
            Log.e("Network", "Search books ${ex.message!!}")

        }

    }
}