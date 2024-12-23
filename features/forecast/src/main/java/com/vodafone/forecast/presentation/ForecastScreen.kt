package com.vodafone.forecast.presentation

import WeatherCard
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vodafone.core.models.DailyForecast
import com.vodafone.shared_ui.R
import com.vodafone.shared_ui.component.MainAppBar
import com.vodafone.shared_ui.utils.ErrorMessage
import com.vodafone.shared_ui.utils.LottieLoader

@Composable
fun ForecastScreen(
    city: String,
    viewModel: ForecastViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(city) {
        viewModel.handleIntent(ForecastIntent.FetchForecast(city))
    }
    Scaffold(
        topBar = { MainAppBar(title = "$city 7-Day Forecast") }
    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            when (state) {
                is ForecastState.Loading -> LottieLoader(animationRes = R.raw.weather_loader)
                is ForecastState.Success -> ForecastList(forecast = (state as ForecastState.Success).data?.forecasts)
                is ForecastState.Error -> ErrorMessage(message = (state as ForecastState.Error).message)
                else -> {}
            }
        }
    }
}

@Composable
fun ForecastList(forecast: List<DailyForecast>?) {
    forecast?.let {
        LazyColumn {
            itemsIndexed(forecast) { _, dayForecast ->
                WeatherCard(dayForecast)
            }
        }
    }
}
