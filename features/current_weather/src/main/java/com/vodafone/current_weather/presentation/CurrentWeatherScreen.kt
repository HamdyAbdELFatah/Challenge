package com.vodafone.current_weather.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.shared_ui.component.MainAppBar
import com.vodafone.shared_ui.component.SearchedWeatherCard
import com.vodafone.shared_ui.utils.ErrorMessage
import com.vodafone.shared_ui.utils.LottieLoader

@Composable
fun CurrentWeatherScreen(
    city: String,
    onNavigateToForecast: (String) -> Unit,
    viewModel: CurrentWeatherViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(city) {
        viewModel.fetchCurrentWeather(city)
    }
    Scaffold(
        topBar = { MainAppBar(title = "$city Weather") }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            when (state) {
                is CurrentWeatherState.Loading -> LottieLoader(animationRes = com.vodafone.shared_ui.R.raw.weather_loader)
                is CurrentWeatherState.Success -> {
                    (state as CurrentWeatherState.Success).data?.let {
                        SearchedWeatherCard(
                            title = "Current Weather",
                            weather = it

                        )
                    }
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Button(
                            onClick = { onNavigateToForecast(city) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("See 7-Day Forecast", color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }

                is CurrentWeatherState.Error -> ErrorMessage(message = (state as CurrentWeatherState.Error).message)
                else -> Text("Enter a city to fetch weather data.")
            }
        }
    }

}
