package com.vodafone.core.models

data class WeatherForecast(
    val city: String,
    val country: String,
    val forecasts: List<DailyForecast>
)

data class DailyForecast(
    val dateTime: String,
    val temperature: Double,
    val weatherCondition: String,
    val weatherDescription: String,
    val icon: String
)
