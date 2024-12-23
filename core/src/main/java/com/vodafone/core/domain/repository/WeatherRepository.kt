package com.vodafone.core.domain.repository

import com.vodafone.core.models.Weather
import com.vodafone.core.models.WeatherForecast
import com.vodafone.core.utils.DataResult
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {
    fun getCurrentWeather(city: String): Flow<DataResult<Weather>>
    suspend fun getLastSearchedWeather(): Flow<DataResult<Weather>>
    suspend fun getForecast(cityName: String): Flow<DataResult<WeatherForecast?>>
}