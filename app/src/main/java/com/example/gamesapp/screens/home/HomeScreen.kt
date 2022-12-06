package com.example.gamesapp.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gamesapp.components.GameCard
import com.example.gamesapp.components.GamesAppBar
import com.example.gamesapp.utils.Constants
import com.example.gamesapp.utils.getPeriodDates

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = hiltViewModel()){
    Scaffold(topBar = {
        GamesAppBar(title = "Games app", navController = navController)
    }
    ){
        Surface(modifier = Modifier
            .fillMaxSize()
        ) {
            HomeContent(navController, viewModel)
        }
    }
}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeViewModel){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        PeriodsSection(viewModel)
        GamesList(navController, viewModel)
    }

}

@Composable
fun PeriodsSection(viewModel: HomeViewModel){
    val selectedPeriod : MutableState<Int> = remember {mutableStateOf(6)}

    Text(
        text = "Upcoming in next",
        style = MaterialTheme.typography.h4,
        color = Color.Red
    )

    Row(){
        for(i in 0..3){
            PeriodButton(Constants.PERIODS[i],  selectedPeriod.value){
                selectedPeriod.value = it
                val periodsDates = getPeriodDates(it.toLong())
                viewModel.getUpcomingBooks(periodsDates)
            }
        }
    }
}

@Composable
fun PeriodButton(
    period: Int,
    selectedPeriod: Int,
    changePeriod : (Int) -> Unit
){
    Button(
        onClick = { changePeriod(period) },
        modifier = Modifier.padding(2.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            backgroundColor = if(period == selectedPeriod) Color.Red else Color.Black
            ),
        shape = CircleShape,
        elevation = ButtonDefaults.elevation(defaultElevation = 10.dp)
    ) {
        Text(
            text = "$period months",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }

}

@Composable
fun GamesList(navController: NavController, viewModel: HomeViewModel){

    val listOfGames = viewModel.list
    if(viewModel.isLoading){
        CircularProgressIndicator(modifier = Modifier.then(Modifier.size(32.dp))
        )
    }
    else{
        LazyColumn(modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(4.dp)
        ){
            items(items = listOfGames){ game ->
                GameCard(game, navController)
            }
        }
    }
}