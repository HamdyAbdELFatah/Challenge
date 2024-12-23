package com.vodafone.forecast.presentation

import com.vodafone.core.models.WeatherForecast

sealed class ForecastState {
    object Idle : ForecastState()
    object Loading : ForecastState()
    data class Success(val data: WeatherForecast?) : ForecastState()
    data class Error(val message: String) : ForecastState()
}

sealed class ForecastEvent {
    data class NavigateToDetails(val day: String) : ForecastEvent()
}