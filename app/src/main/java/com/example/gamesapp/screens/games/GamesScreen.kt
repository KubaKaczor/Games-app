package com.example.gamesapp.screens.games

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gamesapp.components.GameCard
import com.example.gamesapp.components.GamesAppBar
import com.example.gamesapp.data.Resource
import com.example.gamesapp.model.Game

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun GamesScreen(navController: NavController, type: String, id: String, viewModel: GamesViewModel, label: String){

    var games = remember { listOf<Game>() }

    val gamesState = produceState(
        initialValue = Resource.Loading()
    ){
        value = when(type){
            "genre" -> viewModel.getGamesByGenre(id)
            "dev" ->viewModel.getGamesByDev(id)
            else -> viewModel.getGamesByPlatform(id)
        }
    }.value

    if(gamesState.data == null){
        CircularProgressIndicator(modifier = Modifier.then(Modifier.size(32.dp)))
    }else{
        games = gamesState.data!!
    }



    Scaffold(topBar = {
        GamesAppBar(
            title = "Games app",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isHome = false
        ){
            navController.popBackStack()
        }
    }
    ){
        Surface(modifier = Modifier
            .fillMaxSize()
        ) {
            GamesContent(navController, games, label)
        }
    }
}

@Composable
fun GamesContent(navController: NavController, games: List<Game>, label: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Text(
            text = "$label Games:",
            style = MaterialTheme.typography.h4,
            color = Color.Red,
            modifier = Modifier
                .padding(start = 8.dp, bottom = 4.dp)
                .align(Alignment.Start)
        )
        GamesList(navController, games)
    }
}

@Composable
fun GamesList(navController: NavController, games: List<Game>){
    LazyColumn(modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(4.dp)
    ){
        items(items = games){ game ->
            GameCard(game, navController)
        }
    }
}
