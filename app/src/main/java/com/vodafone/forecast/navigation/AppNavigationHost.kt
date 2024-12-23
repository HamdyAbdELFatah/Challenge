package com.vodafone.forecast.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.vodafone.city_input.presentation.CityInputScreen
import com.vodafone.core.navigation.NavRouts
import com.vodafone.current_weather.presentation.CurrentWeatherScreen
import com.vodafone.forecast.presentation.ForecastScreen

@Composable
fun AppNavigationHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavRouts.CityInput) {
        composable(route = NavRouts.CityInput) {
            CityInputScreen(onNavigateToWeather = { city ->
                navController.navigate("${NavRouts.CurrentWeather}/${Uri.encode(city)}")
            })
        }
        composable(
            route = "${NavRouts.CurrentWeather}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            CurrentWeatherScreen(
                city = city,
                onNavigateToForecast = {
                    navController.navigate("${NavRouts.DayForecast}/${Uri.encode(city)}")
                }
            )
        }
        composable(
            route = "${NavRouts.DayForecast}/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            ForecastScreen(city = city)
        }
    }
}


