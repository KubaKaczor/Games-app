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

fun getStoreImageIdByName(name: String): Int{
    var imageId = 0
    with(name) {
        when {
            contains("steam") -> imageId = R.drawable.steam_logo
            contains("epic") -> imageId = R.drawable.epic_logo
            contains("gog") -> imageId = R.drawable.gog_logo
            contains("microsoft") -> imageId = R.drawable.microsoft_logo
            contains("playstation") -> imageId = R.drawable.playstation_logo
            contains("apple") -> imageId = R.drawable.appstore_logo
            contains("nintendo") -> imageId = R.drawable.nintendo_logo
            contains("xbox360") -> imageId = -1
            contains("play.google") -> imageId = R.drawable.googleplay_logo
            contains("itch") -> imageId = R.drawable.itch_logo
            else -> imageId = -1
        }
    }
    return imageId

}