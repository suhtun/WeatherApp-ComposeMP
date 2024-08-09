package org.su.coding

import NavRoute
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import domain.WeatherData
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.jetbrains.compose.ui.tooling.preview.Preview
import ui.ForecastDaysScreen
import ui.TodayScreen

@Composable
@Preview
fun App() {
    MaterialTheme() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = NavRoute.FORECASTDAYS) {
            composable(NavRoute.FORECASTDAYS) {
                ForecastDaysScreen(onShowDetail = { weatherdata, colorCode ->
                    val weatherData = Json.encodeToString(weatherdata)
                    navController.navigate("${NavRoute.TODAY}/$weatherData/$colorCode")
                })
            }
            composable(
                "${NavRoute.TODAY}/{weatherData}/{colorCode}",
                arguments = listOf(navArgument("weatherData") { type = NavType.StringType },
                    navArgument("colorCode") { type = NavType.StringType })
            ) { backStackEntry ->

                val json = backStackEntry.arguments?.getString("weatherData")
                val weatherData = json?.let { Json.decodeFromString<WeatherData>(it) }
                val colorCode = backStackEntry.arguments?.getString("colorCode")?.toInt()
                weatherData?.let {
                    TodayScreen(it,
                        colorCode ?: 0,
                        onBack = { navController.navigateUp() })
                }
            }
        }
    }
}