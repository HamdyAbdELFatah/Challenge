package com.vodafone.data.mapper

import com.vodafone.core.models.Weather
import com.vodafone.data.local.entity.WeatherEntity
import com.vodafone.data.model.WeatherResponse

fun WeatherResponse.toWeatherEntity() = WeatherEntity(
    cityName = this.name ?: "Unknown",
    currentTemperature = this.main?.temp ?: 0.0,
    condition = this.weather?.get(0)?.main ?: "Unknown",
    icon = this.weather?.get(0)?.icon ?: "default_icon",
    lastUpdated = System.currentTimeMillis()
)

fun WeatherResponse.toWeatherDomainModel() = Weather(
    city = this.name ?: "Unknown",
    temperature = this.main?.temp ?: 0.0,
    condition = this.weather?.get(0)?.main ?: "Unknown",
    icon = this.weather?.get(0)?.icon ?: "default_icon"
)

fun WeatherEntity.toWeatherData() = Weather(
    city = this.cityName,
    temperature = this.currentTemperature,
    condition = this.condition,
    icon = this.icon
)

fun WeatherEntity.toWeatherDomainModel() = Weather(
    city = this.cityName,
    temperature = this.currentTemperature,
    condition = this.condition,
    icon = this.icon
)
