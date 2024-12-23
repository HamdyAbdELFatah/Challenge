package com.vodafone.forecast.presentation

sealed class ForecastIntent {
    data class FetchForecast(val city: String) : ForecastIntent()
}