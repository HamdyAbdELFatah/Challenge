package com.vodafone.data.mapper

import com.vodafone.core.models.DailyForecast
import com.vodafone.core.models.WeatherForecast
import com.vodafone.data.model.Forecast
import com.vodafone.data.model.ForecastResponse

fun ForecastResponse.toWeatherForecast(): WeatherForecast {
    return WeatherForecast(
        city = this.city?.name ?: "Unknown",
        country = this.city?.country ?: "Unknown",
        forecasts = this.list?.mapNotNull { it?.toDailyForecast() } ?: emptyList()
    )
}

fun Forecast.toDailyForecast(): DailyForecast {
    return DailyForecast(
        dateTime = this.dtTxt ?: "Unknown",
        temperature = this.main?.temp ?: 0.0,
        weatherCondition = this.weather?.firstOrNull()?.main ?: "Unknown",
        weatherDescription = this.weather?.firstOrNull()?.description ?: "No description",
        icon = this.weather?.firstOrNull()?.icon ?: "default_icon"
    )
}
