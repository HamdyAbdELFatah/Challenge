package com.vodafone.data.remote;

import com.vodafone.data.model.ForecastResponse
import com.vodafone.data.model.WeatherResponse
import com.vodafone.data.remote.ApiConstants.CURRENT_WEATHER
import com.vodafone.data.remote.ApiConstants.DAILY_FORECAST
import retrofit2.Response

import retrofit2.http.GET;
import retrofit2.http.Query;


interface WeatherApi {
    @GET(CURRENT_WEATHER)
    suspend fun getCurrentWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): Response<WeatherResponse>

    @GET(DAILY_FORECAST)
    suspend fun getForecast(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("cnt") days: Int = 7,
        @Query("units") units: String = "metric"
    ): Response<ForecastResponse>
}
