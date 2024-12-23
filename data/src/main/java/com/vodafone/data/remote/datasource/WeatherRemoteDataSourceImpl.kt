package com.vodafone.data.remote.datasource

import com.vodafone.data.model.ForecastResponse
import com.vodafone.data.model.WeatherResponse
import com.vodafone.data.remote.ApiConstants.API_KEY
import com.vodafone.data.remote.WeatherApi
import retrofit2.Response
import javax.inject.Inject

class WeatherRemoteDataSourceImpl @Inject constructor(private val api: WeatherApi) : WeatherRemoteDataSource {
    override suspend fun fetchCurrentWeather(city: String): Response<WeatherResponse> = api.getCurrentWeather(city, API_KEY)
    override suspend fun fetchForecast(city: String): Response<ForecastResponse> = api.getForecast(city, API_KEY)

}