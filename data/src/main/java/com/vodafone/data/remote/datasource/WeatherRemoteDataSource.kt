package com.vodafone.data.remote.datasource

import com.vodafone.data.model.ForecastResponse
import com.vodafone.data.model.WeatherResponse
import retrofit2.Response

interface WeatherRemoteDataSource {
    suspend fun fetchCurrentWeather(city: String): Response<WeatherResponse>

    suspend fun fetchForecast(city: String): Response<ForecastResponse>
}

