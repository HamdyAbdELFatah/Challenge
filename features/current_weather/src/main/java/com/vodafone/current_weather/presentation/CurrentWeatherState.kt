package com.vodafone.current_weather.presentation

import com.vodafone.core.models.Weather

sealed class CurrentWeatherState {
    object Idle : CurrentWeatherState()
    object Loading : CurrentWeatherState()
    data class Success(val data: Weather?) : CurrentWeatherState()
    data class Error(val message: String) : CurrentWeatherState()
}