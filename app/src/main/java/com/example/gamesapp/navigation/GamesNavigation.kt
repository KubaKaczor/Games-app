package com.example.gamesapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusDirection.Companion.In
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gamesapp.screens.games.GamesScreen
import com.example.gamesapp.screens.games.GamesViewModel
import com.example.gamesapp.screens.home.HomeScreen
import com.example.gamesapp.screens.home.HomeViewModel
import com.example.gamesapp.screens.info.InfoScreen
import com.example.gamesapp.screens.info.InfoViewModel
import com.example.gamesapp.screens.search.SearchScreen
import com.example.gamesapp.screens.search.SearchViewModel

@Composable
fun GamesNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screens.HomeScreen.name )
    {
        composable(Screens.HomeScreen.name) {
            val viewModel: HomeViewModel = hiltViewModel()
            HomeScreen(navController = navController, viewModel = viewModel)
        }

        composable(Screens.SearchScreen.name) {
            val viewModel: SearchViewModel = hiltViewModel()
            SearchScreen(navController = navController, viewModel = viewModel)
        }

        val infoName = Screens.InfoScreen.name
        composable("$infoName/{id}", arguments = listOf(navArgument("id"){
            type = NavType.StringType
        })) { backStackEntry ->
            backStackEntry.arguments?.getString("id").let {
                val viewModel: InfoViewModel = hiltViewModel()
                InfoScreen(navController = navController, gameId = it!!, viewModel = viewModel)
            }
        }

        val gamesName = Screens.GamesScreen.name
        composable("$gamesName/{type}/{id}/{value}", arguments = listOf(navArgument("type"){
            type = NavType.StringType
        }, navArgument("id"){
            type = NavType.StringType
        }, navArgument("value"){
            type = NavType.StringType
        })) { backStackEntry ->
            val type = backStackEntry.arguments?.getString("type")
            val id = backStackEntry.arguments?.getString("id")
            val value = backStackEntry.arguments?.getString("value")
            val viewModel: GamesViewModel = hiltViewModel()
            GamesScreen(navController = navController,type = type!!, id = id!!, viewModel = viewModel, label = value!!)

        }
    }
}