package com.example.gamesapp.utils

import com.example.gamesapp.R
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

fun getPeriodDates(months: Long): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")

    val todayString = sdf.format(Date())
    val endDate = LocalDate.now().plusMonths(months).toString()

    return "$todayString,$endDate"
}

fun getStoreAndImage(name: String, getResults : (Int, String) -> Unit){
    var imageId = -1
    var store = ""
    with(name) {
        when {
            contains("steam") ->{
                imageId = com.example.gamesapp.R.drawable.steam_logo
                store = "Steam"
            }
            contains("epic") ->{
                imageId = com.example.gamesapp.R.drawable.epic_logo
                store = "Epic Games Store"
            }
            contains("gog") -> {
                imageId = com.example.gamesapp.R.drawable.gog_logo
                store = "GOG"
            }
            contains("microsoft") -> {
                imageId = com.example.gamesapp.R.drawable.microsoft_logo
                store = "Microsoft"
            }
            contains("playstation") -> {
                imageId = com.example.gamesapp.R.drawable.playstation_logo
                store= "Playstation Store"
            }
            contains("apple") -> {
                imageId = com.example.gamesapp.R.drawable.appstore_logo
                store = "App Store"
            }
            contains("nintendo") -> {
                imageId = com.example.gamesapp.R.drawable.nintendo_logo
                store = "Nintendo"
            }
            contains("xbox360") -> {
                imageId = -1
            }
            contains("play.google") -> {
                imageId = com.example.gamesapp.R.drawable.googleplay_logo
                store = "Google play"
            }
            contains("itch") -> {
                imageId = com.example.gamesapp.R.drawable.itch_logo
                store = "Itch"
            }
            else -> {
                imageId = -1
                store = "Unknown"
            }
        }
    }
    getResults(imageId, store)
}