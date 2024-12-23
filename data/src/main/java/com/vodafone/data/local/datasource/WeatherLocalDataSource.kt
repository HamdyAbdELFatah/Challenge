package com.vodafone.data.local.datasource

import com.vodafone.data.local.entity.WeatherEntity

interface WeatherLocalDataSource {
    suspend fun getLastWeather(): WeatherEntity?
    suspend fun saveWeather(weather: WeatherEntity)
}