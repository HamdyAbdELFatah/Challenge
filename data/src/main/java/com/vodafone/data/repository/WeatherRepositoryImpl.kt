package com.vodafone.data.repository

import com.vodafone.core.base.BaseRepository
import com.vodafone.core.models.Weather
import com.vodafone.core.domain.repository.WeatherRepository
import com.vodafone.core.models.WeatherForecast
import com.vodafone.core.utils.DataResult
import com.vodafone.data.local.datasource.WeatherLocalDataSource
import com.vodafone.data.mapper.toWeatherDomainModel
import com.vodafone.data.mapper.toWeatherEntity
import com.vodafone.data.mapper.toWeatherForecast
import com.vodafone.data.remote.datasource.WeatherRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val remoteDataSource: WeatherRemoteDataSource,
    private val localDataSource: WeatherLocalDataSource
) : BaseRepository(), WeatherRepository {

    override fun getCurrentWeather(city: String): Flow<DataResult<Weather>> = flow {
        emit(DataResult.Loading())
        val localWeather = localDataSource.getLastWeather()
        if (localWeather?.cityName == city) {
            emit(DataResult.Success(localWeather.toWeatherDomainModel()))
        }
        val result = safeApiCall { remoteDataSource.fetchCurrentWeather(city) }
        result.collect { dataResult ->
            when (dataResult) {
                is DataResult.Success -> {
                    val weatherData = dataResult.data!!
                    localDataSource.saveWeather(weatherData.toWeatherEntity())
                    emit(DataResult.Success(weatherData.toWeatherDomainModel()))
                }
                is DataResult.Error -> {
                    emit(DataResult.Error(dataResult.message ?: "Failed to fetch weather data"))
                }
                else -> {}
            }
        }
    }

    override suspend fun getForecast(cityName: String): Flow<DataResult<WeatherForecast?>> = flow {

        safeApiCall {
            remoteDataSource.fetchForecast(cityName)
        }.collect { dataResult ->
            when (dataResult) {
                is DataResult.Success -> {
                    emit(DataResult.Success(dataResult.data?.toWeatherForecast()))
                }
                is DataResult.Error -> {
                    emit(DataResult.Error(dataResult.message ?: "Failed to fetch weather forecast"))
                }
                else -> {
                    emit(DataResult.Loading())
                }
            }
        }
    }
    override suspend fun getLastSearchedWeather(): Flow<DataResult<Weather>> = flow {
        val weatherEntity = localDataSource.getLastWeather()
        if (weatherEntity != null) {
            emit(DataResult.Success(weatherEntity.toWeatherDomainModel()))
        } else {
            emit(DataResult.Error("No data found"))
        }
    }

}