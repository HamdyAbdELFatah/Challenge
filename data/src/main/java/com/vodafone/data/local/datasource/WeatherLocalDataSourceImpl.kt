package com.vodafone.data.local.datasource

import com.vodafone.data.local.WeatherDao
import com.vodafone.data.local.entity.WeatherEntity
import javax.inject.Inject

class WeatherLocalDataSourceImpl @Inject constructor(private val dao: WeatherDao) : WeatherLocalDataSource {
    override suspend fun getLastWeather() = dao.getLastWeather()

    override suspend fun saveWeather(weather: WeatherEntity) = dao.saveWeather(weather)
}