package com.example.gamesapp.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gamesapp.components.GameCard
import com.example.gamesapp.components.GamesAppBar
import com.example.gamesapp.components.InputField
import com.example.gamesapp.navigation.Screens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()){
    Scaffold(topBar = {
        GamesAppBar(
            title = "Search",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isHome = false
        ){
            navController.navigate(Screens.HomeScreen.name)
        }
    }
    ){
        Surface(modifier = Modifier
            .fillMaxSize()
        ) {
            SearchContent(navController, viewModel)
        }
    }
}

@Composable
fun SearchContent(navController: NavController, viewModel: SearchViewModel){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        SearchForm(viewModel = viewModel){
            viewModel.searchGames(it)
        }
        GamesList(navController = navController, viewModel = viewModel)
    }
    
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel,
    loading: Boolean = false,
    placeholder: String = "Type title",
    onSearch: (String) -> Unit = {}
){
    Column() {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(valueState = searchQueryState,
            placeholder = placeholder,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                //searchQueryState.value = ""
                keyboardController?.hide()
            }
        )
    }
}

@Composable
fun GamesList(navController: NavController, viewModel: SearchViewModel){

    val listOfGames = viewModel.list
    if(viewModel.isLoading){
        LinearProgressIndicator(modifier = Modifier.then(Modifier.fillMaxWidth())
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